package lab2;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Lab2 {
    public static void main(String[] args) {
        try {
            Class<ExampleClass> clazz = ExampleClass.class;
            ExampleClass instance = clazz.getDeclaredConstructor().newInstance();

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Repeat.class)) {
                    // Changes:
                    if (Modifier.isPublic(method.getModifiers())) {
                        System.out.println("Skiping public methods: " + method.getName());
                        continue;
                    }

                    Repeat repeatAnnotation = method.getAnnotation(Repeat.class);
                    int timesToRepeat = repeatAnnotation.value();

                    method.setAccessible(true);

                    System.out.println("Calling method " + method.getName() + " " + timesToRepeat + " times:");

                    Class<?>[] paramTypes = method.getParameterTypes();
                    Object[] parameters = createParameters(paramTypes);

                    for (int i = 0; i < timesToRepeat; i++) {
                        method.invoke(instance, parameters);
                    }
                    System.out.println();
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static Object[] createParameters(Class<?>[] paramTypes) {
        Object[] params = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            params[i] = getDefaultValue(paramTypes[i]);
        }
        return params;
    }

    private static Object getDefaultValue(Class<?> type) {
        return switch (type.getName()) {
            case "int" -> 0;
            case "double" -> 0.0;
            case "boolean" -> false;
            case "java.lang.String" -> "default";
            case "long" -> 0L;
            case "float" -> 0.0f;
            case "char" -> ' ';
            case "byte" -> (byte) 0;
            case "short" -> (short) 0;
            default -> null;
        };
    }
}