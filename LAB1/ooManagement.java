
public class ooManagement {

    public static void main(String[] args) {
        // Khởi tạo các đối tượng động vật
        Lion lion = new Lion("Leo", 300, 5);
        Snake snake = new Snake("Boa", 50, 5);
        Monkey monkey = new Monkey("George", 150, "chuoi");
        // hiện thị thông tin của từng đối tượng
        lion.display();
        snake.display();
        monkey.display();
    }

}

class Animal {
    // Lớp Animal Chứa các đặc điểm chung con vật: Tên cân nặng
    protected String name;
    protected double weight;

    // Khởi tạo thông tin cơ bản
    public Animal(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }
}

// Các lớp con Lion, Snake, Monkey (Kế thừa từ Animal qua từ khóa extends)
class Lion extends Animal {

    private double eat;

    public Lion(String name, double weight, double eat) {
        super(name, weight);
        this.eat = eat;
    }

    public void display() {
        System.out.println("Su tu " + name + "nang " + weight + "can va an " + eat + "can thit moi ngay");
    }

    public double getEat() {
        return eat;
    }

    public void setEat(double eat) {
        this.eat = eat;
    }
}

class Snake extends Animal {

    private double length;

    public Snake(String name, double weight, double length) {
        super(name, weight);
        this.length = length;
    }

    public void display() {
        System.out.println("Con ran " + name + "nang " + weight + "can va dai " + length + "met. ");
    }

    public void setLength(double length) {
        this.length = length;
    }
}

class Monkey extends Animal {

    private String favoriteFood;

    public Monkey(String name, double weight, String favoriteFood) {
        super(name, weight);
        this.favoriteFood = favoriteFood;
    }

    public void display() {
        System.out.println("Con khi " + name + " nang " + weight + " Can va thich an " + favoriteFood + ".");
    }

    public void setFavoriteFood(String favoriteFood) {
        this.favoriteFood = favoriteFood;
    }
}
