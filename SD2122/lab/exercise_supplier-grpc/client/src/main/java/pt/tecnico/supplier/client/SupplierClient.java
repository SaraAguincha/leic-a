package pt.tecnico.supplier.client;

import static javax.xml.bind.DatatypeConverter.printHexBinary;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pt.tecnico.supplier.grpc.ProductsRequest;
import pt.tecnico.supplier.grpc.ProductsResponse;
import pt.tecnico.supplier.grpc.SignedResponse;
import pt.tecnico.supplier.grpc.SupplierGrpc;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static javax.xml.bind.DatatypeConverter.printHexBinary;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

public class SupplierClient {

	/**
	 * Set flag to true to print debug messages. The flag can be set using the
	 * -Ddebug command line option.
	 */
	private static final boolean DEBUG_FLAG = (System.getProperty("debug") != null);

	/** Helper method to print debug messages. */
	private static void debug(String debugMessage) {
		if (DEBUG_FLAG)
			System.err.println(debugMessage);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(SupplierClient.class.getSimpleName() + " starting ...");

		ArrayList<Long> nonceList = new ArrayList<>();

		// Receive and print arguments.
		System.out.printf("Received %d arguments%n", args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.printf("arg[%d] = %s%n", i, args[i]);
		}

		// Check arguments.
		if (args.length < 2) {
			System.err.println("Argument(s) missing!");
			System.err.printf("Usage: java %s host port%n", SupplierClient.class.getName());
			return;
		}

		final String host = args[0];
		final int port = Integer.parseInt(args[1]);
		final String target = host + ":" + port;

		// Channel is the abstraction to connect to a service end-point.
		final ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();

		// Create a blocking stub for making synchronous remote calls.
		SupplierGrpc.SupplierBlockingStub stub = SupplierGrpc.newBlockingStub(channel);

		int i = 0;
		while(i < 3){
		// Prepare request.
		ProductsRequest request = ProductsRequest.newBuilder().build();
		System.out.println("Request to send:");
		System.out.println(request.toString());
		debug("in binary hexadecimals:");
		byte[] requestBinary = request.toByteArray();
		debug(printHexBinary(requestBinary));
		debug(String.format("%d bytes%n", requestBinary.length));

		// Make the call using the stub.
		System.out.println("Remote call...");
		SignedResponse response = stub.listProducts(request);

		// verify signature
		try{
			boolean isSignatureValid = verifySignature(response.getSignature().getValue().toByteArray(), response.getResponse().toByteArray());

			if(isSignatureValid){
				System.out.println("Signature is valid! Message accepted! :)");

			  if (nonceList.contains(response.getNonce()))
				System.out.println("Nonce already exists! :(");

          // Print response.
			  else {
				System.out.println("Received response:");
				System.out.println(response.getResponse());
				debug("in binary hexadecimals:");
				byte[] responseBinary = response.getResponse().toByteArray();
				debug(printHexBinary(responseBinary));
				debug(String.format("%d bytes%n", responseBinary.length));

				nonceList.add(response.getNonce());
			  }
			} else {
				System.out.println("Signature is invalid! Message rejected! :(");
			}

		} catch (Exception e){
			System.out.println("Signature verification failed!");
		}
		i++;
	}
		// A Channel should be shutdown before stopping the process.
		channel.shutdownNow();
	}

	public static boolean verifySignature(byte[] value, byte[] message) throws Exception{

		// generate sample AES 16 byte initialization vector
		// byte[] iv = new byte[16];
		// let the system pick a strong secure random generator
		// SecureRandom random = SecureRandom.getInstanceStrong();
		// random.nextBytes(iv);
		byte[] iv = "secret_hash_1234".getBytes();

		SecretKeySpec key = readKey("secret.key");

		// get an AES cipher object
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		IvParameterSpec ips = new IvParameterSpec(iv);
		// decipher digest using the public key
		cipher.init(Cipher.DECRYPT_MODE, key, ips);
		byte[] decipheredDigest = cipher.doFinal(value);

		// get a message digest object using the specified algorithm
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(message);
		byte[] digest = messageDigest.digest();;

		// compare digests
		return Arrays.equals(decipheredDigest, digest);
	}

	public static SecretKeySpec readKey(String resourcePathName) throws Exception {
		System.out.println("Reading key from resource " + resourcePathName + " ...");

		InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePathName);
		byte[] encoded = new byte[fis.available()];
		fis.read(encoded);
		fis.close();

		System.out.println("Key:");
		System.out.println(printHexBinary(encoded));
		SecretKeySpec keySpec = new SecretKeySpec(encoded, "AES");

		return keySpec;
	}

}
