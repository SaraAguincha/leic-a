// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: supplier.proto

package pt.tecnico.supplier.grpc;

public interface SignedResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:pt.tecnico.supplier.grpc.SignedResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.pt.tecnico.supplier.grpc.ProductsResponse response = 1;</code>
   * @return Whether the response field is set.
   */
  boolean hasResponse();
  /**
   * <code>.pt.tecnico.supplier.grpc.ProductsResponse response = 1;</code>
   * @return The response.
   */
  pt.tecnico.supplier.grpc.ProductsResponse getResponse();
  /**
   * <code>.pt.tecnico.supplier.grpc.ProductsResponse response = 1;</code>
   */
  pt.tecnico.supplier.grpc.ProductsResponseOrBuilder getResponseOrBuilder();

  /**
   * <code>.pt.tecnico.supplier.grpc.Signature signature = 2;</code>
   * @return Whether the signature field is set.
   */
  boolean hasSignature();
  /**
   * <code>.pt.tecnico.supplier.grpc.Signature signature = 2;</code>
   * @return The signature.
   */
  pt.tecnico.supplier.grpc.Signature getSignature();
  /**
   * <code>.pt.tecnico.supplier.grpc.Signature signature = 2;</code>
   */
  pt.tecnico.supplier.grpc.SignatureOrBuilder getSignatureOrBuilder();

  /**
   * <code>int64 nonce = 3;</code>
   * @return The nonce.
   */
  long getNonce();
}
