package pt.tecnico.supplier.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: supplier.proto")
public final class SupplierGrpc {

  private SupplierGrpc() {}

  public static final String SERVICE_NAME = "pt.tecnico.supplier.grpc.Supplier";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<pt.tecnico.supplier.grpc.ProductsRequest,
      pt.tecnico.supplier.grpc.SignedResponse> getListProductsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "listProducts",
      requestType = pt.tecnico.supplier.grpc.ProductsRequest.class,
      responseType = pt.tecnico.supplier.grpc.SignedResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.supplier.grpc.ProductsRequest,
      pt.tecnico.supplier.grpc.SignedResponse> getListProductsMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.supplier.grpc.ProductsRequest, pt.tecnico.supplier.grpc.SignedResponse> getListProductsMethod;
    if ((getListProductsMethod = SupplierGrpc.getListProductsMethod) == null) {
      synchronized (SupplierGrpc.class) {
        if ((getListProductsMethod = SupplierGrpc.getListProductsMethod) == null) {
          SupplierGrpc.getListProductsMethod = getListProductsMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.supplier.grpc.ProductsRequest, pt.tecnico.supplier.grpc.SignedResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "listProducts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.supplier.grpc.ProductsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.supplier.grpc.SignedResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SupplierMethodDescriptorSupplier("listProducts"))
              .build();
        }
      }
    }
    return getListProductsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SupplierStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SupplierStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SupplierStub>() {
        @java.lang.Override
        public SupplierStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SupplierStub(channel, callOptions);
        }
      };
    return SupplierStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SupplierBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SupplierBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SupplierBlockingStub>() {
        @java.lang.Override
        public SupplierBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SupplierBlockingStub(channel, callOptions);
        }
      };
    return SupplierBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SupplierFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SupplierFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SupplierFutureStub>() {
        @java.lang.Override
        public SupplierFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SupplierFutureStub(channel, callOptions);
        }
      };
    return SupplierFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class SupplierImplBase implements io.grpc.BindableService {

    /**
     */
    public void listProducts(pt.tecnico.supplier.grpc.ProductsRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.supplier.grpc.SignedResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListProductsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getListProductsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.supplier.grpc.ProductsRequest,
                pt.tecnico.supplier.grpc.SignedResponse>(
                  this, METHODID_LIST_PRODUCTS)))
          .build();
    }
  }

  /**
   */
  public static final class SupplierStub extends io.grpc.stub.AbstractAsyncStub<SupplierStub> {
    private SupplierStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SupplierStub(channel, callOptions);
    }

    /**
     */
    public void listProducts(pt.tecnico.supplier.grpc.ProductsRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.supplier.grpc.SignedResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListProductsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SupplierBlockingStub extends io.grpc.stub.AbstractBlockingStub<SupplierBlockingStub> {
    private SupplierBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SupplierBlockingStub(channel, callOptions);
    }

    /**
     */
    public pt.tecnico.supplier.grpc.SignedResponse listProducts(pt.tecnico.supplier.grpc.ProductsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListProductsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SupplierFutureStub extends io.grpc.stub.AbstractFutureStub<SupplierFutureStub> {
    private SupplierFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SupplierFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.supplier.grpc.SignedResponse> listProducts(
        pt.tecnico.supplier.grpc.ProductsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListProductsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_PRODUCTS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SupplierImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SupplierImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST_PRODUCTS:
          serviceImpl.listProducts((pt.tecnico.supplier.grpc.ProductsRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.supplier.grpc.SignedResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SupplierBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SupplierBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pt.tecnico.supplier.grpc.SupplierProtobuf.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Supplier");
    }
  }

  private static final class SupplierFileDescriptorSupplier
      extends SupplierBaseDescriptorSupplier {
    SupplierFileDescriptorSupplier() {}
  }

  private static final class SupplierMethodDescriptorSupplier
      extends SupplierBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SupplierMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SupplierGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SupplierFileDescriptorSupplier())
              .addMethod(getListProductsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
