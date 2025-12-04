# MediatR

MediatR là một thư viện Java nhỏ gọn implement mẫu thiết kế **Mediator**, giúp giảm sự phụ thuộc trực tiếp giữa các thành phần trong ứng dụng bằng cách để chúng giao tiếp thông qua một đối tượng trung gian (Bus).

Thư viện này được lấy cảm hứng từ thư viện MediatR nổi tiếng trong hệ sinh thái .NET.

## Cài đặt

Để sử dụng thư viện này thông qua JitPack:

### Gradle

1. Thêm repository vào `build.gradle` (ở level project hoặc module):

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
```

2. Thêm dependency:

```groovy
dependencies {
    implementation 'com.github.minhhien-e:MediatR:1.0.0'
}
```

### Maven

1. Thêm repository vào `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

2. Thêm dependency:

```xml
<dependency>
    <groupId>com.github.minhhien-e</groupId>
    <artifactId>MediatR</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Cách sử dụng

### 1. Định nghĩa Request

Tạo một class implement interface `BusRequest`. Đây là object mang dữ liệu bạn muốn gửi đi.

```java
import io.github.mediatR.api.BusRequest;

public class PingRequest implements BusRequest {
    private final String message;

    public PingRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
```

### 2. Định nghĩa Handler

Tạo một class implement `RequestHandler<Q, R>`, trong đó `Q` là loại Request và `R` là loại kết quả trả về.

```java
import io.github.mediatR.api.RequestHandler;

public class PingHandler implements RequestHandler<PingRequest, String> {
    
    @Override
    public String execute(PingRequest request) {
        return "Pong: " + request.getMessage();
    }

    @Override
    public Class<?> getRequestClass() {
        return PingRequest.class;
    }
}
```

### 3. Khởi tạo và sử dụng Bus

Bạn cần khởi tạo `BusImpl` và đăng ký các handler. Trong môi trường Spring Boot, việc này có thể được tự động hóa.

```java
import io.github.mediatR.api.Bus;
import io.github.mediatR.core.BusImpl;
import io.github.mediatR.core.BusRequestWrapper; // Lưu ý về Wrapper (nếu cần thiết theo implementation hiện tại)

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // 1. Đăng ký handlers
        Map<String, RequestHandler> handlers = new HashMap<>();
        handlers.put("pingHandler", new PingHandler());

        // 2. Khởi tạo Bus
        Bus bus = new BusImpl(handlers);

        // 3. Gửi request
        // Lưu ý: Tùy thuộc vào implementation hiện tại của BusImpl, 
        // bạn có thể cần bọc request hoặc gửi trực tiếp.
        // Dưới đây là ví dụ gửi request (giả sử implementation hỗ trợ gửi trực tiếp hoặc tự wrap)
        
        // PingRequest request = new PingRequest("Hello");
        // String response = bus.send(request);
        // System.out.println(response);
    }
}
```

## Tính năng

*   **Decoupling**: Tách biệt logic xử lý (Handler) khỏi nơi gọi (Sender).
*   **Type Safety**: Sử dụng Generics để đảm bảo kiểu dữ liệu trả về.
*   **Lightweight**: Không có dependency nặng nề.

