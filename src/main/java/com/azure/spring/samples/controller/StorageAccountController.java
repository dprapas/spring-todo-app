package com.azure.spring.samples.controller;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageAccountController {

    private static Logger logger = LoggerFactory.getLogger(StorageAccountController.class);

    public static final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=storagetodos;" +
            "AccountKey=12nO4rvjxljS/qVuzhWk6jPjcYQ8JU3VZqRKU/Uacgj4+AxOLptseTxMcHSovYyDY3ndtOg0Y+QUuco2SGlndg==;" +
            "EndpointSuffix=core.windows.net";

    public StorageAccountController() {
    }

    @RequestMapping(value = "/storage/{fileName}",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getFile(@PathVariable("fileName") String fileName) {
        logger.info("GET request access '/storage/{}' path.", fileName);

        CloudStorageAccount storageAccount;
        CloudBlobClient blobClient = null;
        CloudBlobContainer container = null;

        try {
            // Parse the connection string and create a blob client to interact with Blob storage
            storageAccount = CloudStorageAccount.parse(storageConnectionString);
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference("todosdata");

            //Listing contents of container
            for (ListBlobItem blobItem : container.listBlobs()) {
                logger.info("URI of blob is: " + blobItem.getUri());
            }

            //Getting a blob reference
            CloudBlockBlob blob = container.getBlockBlobReference(fileName);
            String content = blob.downloadText();
            logger.info("Content: " + content);

        } catch (StorageException ex) {
            logger.error(String.format("Error returned from the service. Http code: %d and error code: %s",
                    ex.getHttpStatusCode(), ex.getErrorCode()));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            logger.info("The program has completed successfully.");
        }

        return new ResponseEntity<String>("OK", HttpStatus.OK);

//        try {
//            return new ResponseEntity<TodoItem>(todoItemRepository.findById(index).get(), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<String>(index + " not found", HttpStatus.NOT_FOUND);
//        }
    }

}
