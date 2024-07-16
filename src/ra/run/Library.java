package ra.run;

import ra.entity.Book;
import ra.entity.Category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Library {
    public static List<Category> listCategories = new ArrayList<Category>();
    public static List<Book> listBooks = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("************QUẢN LÝ THƯ VIỆN*************");
            System.out.println("1. Quản lý thể loại");
            System.out.println("2. Quản lý sách");
            System.out.println("3. Thoát");
            System.out.print("Lựa chọn của bạn:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    Library.displayMenuCategory(scanner);
                    break;
                case 2:
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.err.println("Vui lòng chọn từ 1-3");
            }
        } while (true);
    }

    public static void displayMenuCategory(Scanner scanner) {
        boolean isExit = true;
        do {
            System.out.println("************QUẢN LÝ THỂ LOẠI*************");
            System.out.println("1. Thêm mới thể loại");
            System.out.println("2. Hiển thị danh sách thể loại Anphabet");
            System.out.println("3. Thống kê thể loại và số lượng sách trong từng thể loại");
            System.out.println("4. Cập nhật thể loại");
            System.out.println("5. Xóa thể loại");
            System.out.println("6. Quay lại");
            System.out.println("Lựa chọn của bạn:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    Library.addNewCategory(scanner);
                    break;
                case 2:
                    Library.displayCategoriesASC();
                    break;
                case 3:
                    Library.staticticCategory();
                    break;
                case 4:
                    Library.updateCategory(scanner);
                    break;
                case 5:
                    Library.deleteCategories(scanner);
                    break;
                case 6:
                    isExit = false;
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1-6");
            }
        } while (isExit);
    }

    public static void addNewCategory(Scanner scanner) {
        //Thêm 1 thể loại
        Category catNew = new Category();
        catNew.input(scanner);
        listCategories.add(catNew);
    }

    public static void displayCategoriesASC() {
        listCategories.stream().sorted(Comparator.comparing(Category::getName)).forEach(Category::output);
    }

    public static void staticticCategory() {
        for (Category cat : listCategories) {
            //Thống kê cat
            System.out.printf("0%d - %s : %d sách\n",cat.getId(), cat.getName(),
                    listBooks.stream().filter(book -> book.getCategoryId() == cat.getId()).count());
        }
    }

    public static void updateCategory(Scanner scanner) {
        System.out.println("Nhập vào mã thể loại cần câp nhật:");
        int idUpdate = Integer.parseInt(scanner.nextLine());
        int indexUpdate = getIndexById(idUpdate);
        if (indexUpdate >= 0) {
            boolean isExist = true;
            do {
                System.out.println("1. Cập nhật tên danh mục");
                System.out.println("2. Cập nhật trạng thái danh mục");
                System.out.println("3. Thoát");
                System.out.print("Lựa chọn của bạn:");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        listCategories.get(indexUpdate).setName(scanner.nextLine());
                        break;
                    case 2:
                        listCategories.get(indexUpdate).setStatus(Boolean.parseBoolean(scanner.nextLine()));
                        break;
                    case 3:
                        isExist = false;
                        break;
                    default:
                        System.err.println("Vui lòng nhập từ 1-3");

                }
            } while (isExist);
        } else {
            System.err.println("Mã danh mục cập nhật không tồn tại");
        }

    }

    public static int getIndexById(int id) {
        for (int i = 0; i < listCategories.size(); i++) {
            if (listCategories.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public static void deleteCategories(Scanner scanner) {
        System.out.println("Nhập vào mã thể loại cần xóa:");
        int idDelete = Integer.parseInt(scanner.nextLine());
        int indexDelete = getIndexById(idDelete);
        if (indexDelete >= 0) {
            //Kiểm tra trong thể loại có sách chưa?
            boolean isExist = listBooks.stream().anyMatch(book -> book.getCategoryId() == idDelete);
            if (isExist) {
                System.err.println("Thể loại sách đang chứa sách, không thể xóa đuợc");
            } else {
                listCategories.remove(indexDelete);
            }
        } else {
            System.err.println("Mã thể loại xóa không tồn tại");
        }
    }

    public static void displayBookByCategory() {
        //Chức năng 5 của quản lý sách
        for (Category cat : listCategories) {
            System.out.printf("%s : %d sách\n", cat.getName(),
                    listBooks.stream().filter(book -> book.getCategoryId() == cat.getId()).count());
            listBooks.stream().filter(book -> book.getCategoryId() == cat.getId())
                    .forEach(book -> System.out.printf("\t%s\n", book.getTitle()));
        }
    }
}
