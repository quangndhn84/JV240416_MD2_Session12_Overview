package ra.entity;

import ra.run.Library;

import java.util.Comparator;
import java.util.Scanner;

public class Category implements IEntity {
    private int id;
    private String name;
    private boolean status;

    public Category() {
    }

    public Category(int id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public void input(Scanner scanner) {
        this.id = inputId(scanner);
        this.name = inputName(scanner);
        this.status = inputStatus(scanner);
    }

    public int inputId(Scanner scanner) {
        //Tu sinh cac id tang dan bat dau tu 1 --> lay id max + 1
        if (Library.listCategories.size() == 0) {
            return 1;
        }
        //Tim max id trong listCategories
        int idMax = Library.listCategories.stream().max(Comparator.comparing(catalog -> catalog.id)).get().getId();
        return idMax + 1;
    }

    public String inputName(Scanner scanner) {
        System.out.println("Nhập vào tên thể loại:");
        do {
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) {
                System.err.println("Tên thể loại không được để trống, vui lòng nhập lại");
            } else {
                if (name.length() >= 6 && name.length() <= 30) {
                    boolean isExist = Library.listCategories.stream().anyMatch(catalog -> catalog.getName().equals(name));
                    if (isExist) {
                        System.err.println("Tên danh mục đã tồn tại, vui lòng nhập lại");
                    } else {
                        return name;
                    }
                } else {
                    System.err.println("Tên thể loai có độ dài từ 6-30 ký tự, vui lòng nhập lại");
                }
            }
        } while (true);
    }

    public boolean inputStatus(Scanner scanner) {
        System.out.println("Nhập vào trạng thái thể loại sách:");
        do {
            String status = scanner.nextLine();
            if (status.equals("true") || status.equals("false")) {
                return Boolean.parseBoolean(status);
            } else {
                System.err.println("Trạng thái danh mục sách chỉ nhận true || false, vui lòng nhập lại");
            }
        } while (true);
    }

    @Override
    public void output() {
        System.out.printf("Mã thể loại: %d - Tên thể loại: %s - trạng thái: %s\n",
                this.id, this.name, this.status ? "Hoạt động" : "Không hoạt động");
    }
}
