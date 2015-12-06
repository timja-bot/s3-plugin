package hudson.plugins.s3.callable;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.transfer.TransferManager;
import hudson.plugins.s3.ClientHelper;
import hudson.util.Secret;

import java.io.Serializable;

public class AbstractS3Callable implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final String accessKey;
    private final Secret secretKey;
    private final boolean useRole;
    private transient AmazonS3Client client;
    private transient TransferManager transferManager;

    public AbstractS3Callable(String accessKey, Secret secretKey, boolean useRole)
    {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.useRole = useRole;
    }

    protected AmazonS3Client getClient()
    {
        if (client == null) {
            client = ClientHelper.createClient(accessKey, secretKey, useRole);
        }
        return client;
    }

    protected TransferManager getTransferManager()
    {
        if (transferManager == null)
            transferManager = new TransferManager(getClient());

        return transferManager;
    }

}
