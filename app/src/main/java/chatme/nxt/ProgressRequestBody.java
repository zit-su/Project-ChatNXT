package chatme.nxt;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Buffer;
import java.io.IOException;

public class ProgressRequestBody extends RequestBody {
    private RequestBody requestBody;
    private ProgressListener listener;

    public interface ProgressListener {
        void onProgress(long bytesWritten, long contentLength);
    }

    public ProgressRequestBody(RequestBody requestBody, ProgressListener listener) {
        this.requestBody = requestBody;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        BufferedSink bufferedSink = Okio.buffer(new ForwardingSink(sink) {
            long bytesWritten = 0;
            long contentLength = 0;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    contentLength = contentLength();
                }
                bytesWritten += byteCount;
                listener.onProgress(bytesWritten, contentLength);
            }
        });
        
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }
}