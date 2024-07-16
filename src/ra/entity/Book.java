package ra.entity;

import ra.run.Library;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Book implements IEntity {
    private String id;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private String description;
    private int categoryId;

    public Book() {
    }

    public Book(String id, String title, String author, String publisher, int year, String description, int categoryId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.description = description;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void input(Scanner scanner) {
        this.id = inputId(scanner);
        this.title = inputTitle(scanner);
        this.author = inputAuthor(scanner);
        this.publisher = inputPublisher(scanner);
        this.year = inputYear(scanner);
        this.description = inputDescription(scanner);
        this.categoryId = inputCategoryId(scanner);
    }

    public String inputId(Scanner scanner) {
        String idRegex = "B[\\w]{3}";
        System.out.println("Nhập vào mã sách:");
        do {
            String id = scanner.nextLine();
            if (Pattern.matches(idRegex, id)) {
                if (Library.listBooks.stream().anyMatch(book -> book.getId().equals(id))) {
                    System.err.println("Mã sách đã tồn tại, vui lòng nhập lại");
                } else {
                    return id;
                }
            } else {
                System.err.println("Mã sách sai định dạng, vui lòng nhập lại");
            }
        } while (true);
    }

    public String inputTitle(Scanner scanner) {
        System.out.println("Nhập vào tiêu đề sách:");
        do {
            String title = scanner.nextLine();
            if (title.trim().length() >= 6 && title.trim().length() <= 50) {
                return title.trim();
            } else {
                System.err.println("Tiêu đề sách có từ 6-50 ký tự, vui lòng nhập lại");
            }
        } while (true);
    }

    public String inputAuthor(Scanner scanner) {
        System.out.println("Nhập vào tên tác giả:");
        do {
            String author = scanner.nextLine();
            if (author.trim().isEmpty()) {
                System.err.println("Tác giả không được để trống, vui lòng nhập lại");
            } else {
                return author.trim();
            }
        } while (true);
    }

    public String inputPublisher(Scanner scanner) {
        System.out.println("Nhập vào nhà xuất bản:");
        do {
            String publisher = scanner.nextLine();
            if (publisher.trim().isEmpty()) {
                System.err.println("Nhà xuất bản không được để trống, vui lòng nhập lại");
            } else {
                return publisher.trim();
            }
        } while (true);
    }

    public int inputYear(Scanner scanner) {
        System.out.println("Nhập vào năm xuất bản:");
        do {
            String year = scanner.nextLine();
            try {
                int bookYear = Integer.parseInt(year);
                int yearNow = LocalDate.now().getYear();
                if (bookYear >= 1970 && bookYear <= yearNow) {
                    return bookYear;
                } else {
                    System.err.println("Năm xuất bản từ 1970 đến hiện nay, vui lòng nhập lại");
                }
            } catch (Exception ex) {
                System.err.println("Năm xuất bản là số nguyên, vui lòng nhập lại");
            }
        } while (true);
    }

    public String inputDescription(Scanner scanner) {
        System.out.println("Nhập vào mô tả:");
        do {
            String description = scanner.nextLine();
            if (description.trim().isEmpty()) {
                System.err.println("Mô tả không được để trống, vui lòng nhập lại");
            } else {
                return description.trim();
            }
        } while (true);
    }

    public int inputCategoryId(Scanner scanner) {
        System.out.println("Chọn danh mục sách:");
        for (int i = 0; i < Library.listCategories.size(); i++) {
            System.out.printf("%d. %s\n", (i + 1), Library.listCategories.get(i).getName());
        }
        System.out.print("Lựa chọn của bạn:");
        int choice = Integer.parseInt(scanner.nextLine());
        return Library.listCategories.get(choice - 1).getId();
    }

    @Override
    public void output() {
        System.out.printf("Mã sách: %s - Tiêu đề: %s - Tác giả: %s - NXB: %s\n",
                this.id, this.title, this.author, this.publisher);
        System.out.printf("Năm XB: %d - Thể loại: %s - Mô tả: %s\n",
                this.year, Library.listCategories.stream()
                        .filter(category -> category.getId() == this.categoryId).toList().get(0).getName(), this.description);
    }
}
