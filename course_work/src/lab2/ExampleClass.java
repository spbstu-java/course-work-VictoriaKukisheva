package lab2;

@SuppressWarnings("unused")
public class ExampleClass {
    @Repeat(3)
    public void publicMethod(String name, int number) {
        System.out.println("Public method called " + name + " " + number);
    }

    @Repeat(2)
    protected void protectedMethod(double value) {
        System.out.println("Protected method called " + value);
    }

    @Repeat(1)
    private void privateMethod() {
        System.out.println("Private method called");
    }

    @Repeat(1)
    public void anotherPublicMethod() {
        System.out.println("This method is not annotated");
    }
}
