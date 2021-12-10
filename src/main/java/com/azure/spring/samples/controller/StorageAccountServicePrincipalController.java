package com.azure.spring.samples.controller;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gets access to the storage account via a service principal. The service principal
 * has the Contributor role in the Azure Subscription and therefore the role is propagated
 * to all resources underneath the subscription. In this case the storage account
 */
@RestController
public class StorageAccountServicePrincipalController {

    private static Logger logger = LoggerFactory.getLogger(StorageAccountServicePrincipalController.class);

    private static final String CLIENT_ID = "6e0c619a-f3e6-4877-8f29-ccc8b89ad28c";
    private static final String CLIENT_SECRET = "sjj7Q~ieqFNn4SS5uRT.DvMr6AKm60mFG4_BD";
    private static final String TENANT_ID = "a30ea581-6951-4a4f-b691-3b104b58ead1";

    public StorageAccountServicePrincipalController() {
    }

    @RequestMapping(value = "/storage/sp/{fileName}",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getFile(@PathVariable("fileName") String fileName) {
        logger.info("GET request access '/storage/sp/{}' path.", fileName);

        /**
         *  Authenticate with client secret.
         */
        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .tenantId(TENANT_ID)
                .build();

        BlobServiceClient storageClient = new BlobServiceClientBuilder()
                .endpoint("https://extblobs.blob.core.windows.net")
                .credential(clientSecretCredential)
                .buildClient();

        BlobContainerClient containerClient = storageClient.getBlobContainerClient("extdptodoblobs");
        BlobClient blobClient = containerClient.getBlobClient("todos-storage.txt");
        String url = blobClient.getBlobUrl();

        System.out.println("Successfully setup client using the Azure Identity, please check the service version: "
                + storageClient.getProperties().getDefaultServiceVersion());


        logger.info("Blob URL: " + url);
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
}
