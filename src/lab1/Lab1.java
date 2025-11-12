package lab1;

import java.util.Scanner;

public class Lab1 {
    public static void main(String[] args) {
        Hero hero = new Hero();

        System.out.println("The little hero wake up and his day starts...\nHe get out from home.");

        while (true) {
            System.out.println("Choose what he will do: ");
            System.out.println(" 1 - Walk");
            System.out.println(" 2 - Ride");
            System.out.println(" 3 - Fly");
            System.out.println(" 0 - End day");

            int choise;
            Scanner scanner = new Scanner(System.in);

            try {
                choise = Integer.parseInt(scanner.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("Enter a number");
                continue;
            }

            if (choise == 0) {
                System.out.println("Day ends. Lets sleep");
                break;
            }

            switch (choise){
                case 1:
                    hero.setMoveStrategy(new WalkStrategy());
                    break;
                case 2:
                    hero.setMoveStrategy(new RideStrategy());
                    break;
                case 3:
                    hero.setMoveStrategy(new FlyStrategy());
                    break;
                default:
                    System.out.println("I dont know what to do");
                    continue;
            }
            hero.move();
        }
    }
}