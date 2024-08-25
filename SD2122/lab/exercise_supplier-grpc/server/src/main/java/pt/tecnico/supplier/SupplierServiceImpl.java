package pt.tecnico.supplier;

import static javax.xml.bind.DatatypeConverter.printHexBinary;

import com.google.protobuf.ByteString;
import com.google.type.Money;

import io.grpc.stub.StreamObserver;
import pt.tecnico.supplier.domain.Supplier;
import pt.tecnico.supplier.grpc.*;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static javax.xml.bind.DatatypeConverter.printHexBinary;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

public class SupplierServiceImpl extends SupplierGrpc.SupplierImplBase {

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

	/** Domain object. */
	final private Supplier supplier = Supplier.getInstance();

	/** Constructor */
	public SupplierServiceImpl() {
		debug("Loading demo data...");
		supplier.demoData();
	}

	/** Helper method to convert domain product to message product. */
	private Product buildProductFromProduct(pt.tecnico.supplier.domain.Product p) {
		Product.Builder productBuilder = Product.newBuilder();
		productBuilder.setIdentifier(p.getId());
		productBuilder.setDescription(p.getDescription());
		productBuilder.setQuantity(p.getQuantity());
		productBuilder.setAvailable(p.isAvailable());

		Money.Builder moneyBuilder = Money.newBuilder();
		moneyBuilder.setCurrencyCode("EUR").setUnits(p.getPrice());
		productBuilder.setPrice(moneyBuilder.build());

		return productBuilder.build();
	}

	@Override
	public void listProducts(ProductsRequest request, StreamObserver<SignedResponse> responseObserver) {
		debug("listProducts called");

		debug("Received request:");
		debug(request.toString());
		debug("in binary hexadecimals:");
		byte[] requestBinary = request.toByteArray();
		debug(String.format("%d bytes%n", requestBinary.length));

		// build response
		ProductsResponse.Builder responseBuilder = ProductsResponse.newBuilder();
		responseBuilder.setSupplierIdentifier(supplier.getId());
		for (String pid : supplier.getProductsIDs()) {
			pt.tecnico.supplier.domain.Product p = supplier.getProduct(pid);
			Product product = buildProductFromProduct(p);
			responseBuilder.addProduct(product);
		}
		ProductsResponse response = responseBuilder.build();

		debug("Response to send:");
		debug(response.toString());
		debug("in binary hexadecimals:");
		byte[] responseBinary = response.toByteArray();
		debug(printHexBinary(responseBinary));
		debug(String.format("%d bytes%n", responseBinary.length));

		// sign response
		try{
			byte[] cypherResponse = this.digestAndCypher(responseBinary);

			Random random = new Random();
			Long nonce = random.nextLong();
			//Long nonce = 12343L;

			Signature signature = Signature.newBuilder()
					.setValue(ByteString.copyFrom(cypherResponse))
					.setSignerId(supplier.getId())
					.build();

			SignedResponse signedResponse = SignedResponse.newBuilder()
					.setSignature(signature)
					.setNonce(nonce)
					.setResponse(response)
					.build();

			// send single response back
			responseObserver.onNext(signedResponse);
			// complete call
			responseObserver.onCompleted();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] digestAndCypher(byte[] bytes) throws Exception {

		// generate sample AES 16 byte initialization vector
		//byte[] iv = new byte[16];
		// let the system pick a strong secure random generator
		// SecureRandom random = SecureRandom.getInstanceStrong();
		// random.nextBytes(iv);
		byte[] iv = "secret_hash_1234".getBytes();

		SecretKeySpec key = readKey("secret.key");

		// get a message digest object using the specified algorithm
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(bytes);
		byte[] digest = messageDigest.digest();

		// get an AES cipher object
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		IvParameterSpec ips = new IvParameterSpec(iv);
		// encrypt the plain text using the key
		cipher.init(Cipher.ENCRYPT_MODE, key, ips);

		return cipher.doFinal(digest);
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
