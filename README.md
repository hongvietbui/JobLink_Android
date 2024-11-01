##1. data/ Layer
apis/: Chứa các interface định nghĩa API của Retrofit.\\
dataSources/: Tách biệt các nguồn dữ liệu như RemoteDataSource, LocalDataSource, giúp dễ dàng chuyển đổi giữa các nguồn dữ liệu nếu cần.\\
mappers/: Tách riêng lớp mapper giúp chuyển đổi dữ liệu giữa data models và domain models. Điều này tuân thủ nguyên tắc Single Responsibility Principle (SRP).\\
models/: Đây là nơi chứa các lớp dữ liệu (DTO - Data Transfer Objects) nhận từ API hoặc lưu trữ trong database.\\
repositoryImpls/: Là nơi triển khai chi tiết các Repository định nghĩa trong domain, tách biệt phần triển khai này để giữ domain không phụ thuộc vào data.\\

##2. domain/ Layer
models/: Chứa các domain models đại diện cho dữ liệu và logic nghiệp vụ của ứng dụng. Đặt riêng models ở đây giúp tách biệt các lớp dữ liệu từ data layer.\\
repositories/: Interface của Repository, định nghĩa các phương thức cần thiết cho useCases. Đây là nơi tách rõ ràng logic nghiệp vụ và logic lấy dữ liệu.\\
useCases/: Rất tốt khi tách riêng useCases để xử lý các nghiệp vụ (business logic). Các useCases này nên đơn giản và chỉ tập trung vào một nhiệm vụ cụ thể.\\

##3. presentation/ Layer
activities/ và fragments/: Việc tách riêng Activity và Fragment giúp phân biệt rõ ràng hai thành phần này trong giao diện người dùng.\\
viewModels/: Chứa các ViewModel để quản lý logic của giao diện và dữ liệu tương ứng. Đây là nơi kết nối dữ liệu từ domain đến presentation.\\
views/: Có thể bạn sử dụng để chứa các View tùy chỉnh hoặc các layout cụ thể. Điều này giúp quản lý UI components dễ dàng.\\
DI/: Thư mục dành riêng cho Dependency Injection (Dagger, Hilt, vv). Việc tách riêng các module DI vào đây giúp dễ dàng quản lý các thành phần được inject.\\