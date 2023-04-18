package src.config.metadata;

import org.springframework.stereotype.Service;

@Service
public class MetadataService {
    private final ThreadLocal<String> metadata = new ThreadLocal<>();

    public void setMetadata(String metadataValue) {
        metadata.set(metadataValue);
    }

    public String getMetadata() {
        return metadata.get();
    }

    public void clearMetadata() {
        metadata.remove();
    }
}