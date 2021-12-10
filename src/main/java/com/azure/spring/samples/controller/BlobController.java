package com.azure.spring.samples.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Gets access to the storage account with an access key. Check the access key in the application.properties
 */
@RestController
@RequestMapping("blob")
@ConditionalOnProperty("azure.storage.blob-endpoint")
public class BlobController {

    private static Logger logger = LoggerFactory.getLogger(BlobController.class);

    public BlobController() {
    }

    @Value("${resource.blob}")
    private Resource azureBlobResource;

    @GetMapping
    public String readBlobResource() throws IOException {
        return StreamUtils.copyToString(
                this.azureBlobResource.getInputStream(),
                Charset.defaultCharset());
    }

    @PostMapping
    public String writeBlobResource(@RequestBody String data) throws IOException {
        try (OutputStream os = ((WritableResource) this.azureBlobResource).getOutputStream()) {
            os.write(data.getBytes());
        }
        return "blob was updated";
    }

}
