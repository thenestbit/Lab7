package Utils;

import CollectionObject.Coordinates;
import CollectionObject.CityModel;
import CollectionObject.Human;
import CollectionObject.StandardOfLiving;
import Exceptions.EmptyFieldException;
import Exceptions.NegativeFieldException;
import Network.User;

import java.util.Date;
import java.util.Scanner;


public class CityAsker {

    private static String askString(Scanner InputScanner) {
        while(true) {
            try {
                var name = InputScanner.nextLine();
                if (name.isBlank()){
                    throw new EmptyFieldException("Field can't be empty. Re-enter it, please: ");
                }
                return name.trim();
            }catch(EmptyFieldException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static float askX(Scanner InputScanner) {
        while(true) {
            try {
                return Float.parseFloat(InputScanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Wrong number format. Re-enter it please: ");
            }
        }
    }

    private static double askY(Scanner InputScanner) {
        while(true) {
            try {
                return Double.parseDouble(InputScanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Wrong number format. Re-enter it please: ");
            }
        }
    }

    private static float askFloat(Scanner InputScanner) {
        while(true) {
            try {
                float num = Float.parseFloat(InputScanner.nextLine());
                if (num > 0){
                    return num;
                } else {
                    throw new NegativeFieldException("Wrong number format (it can't be less than 0). Re-enter it please: ");
                }

            } catch (NumberFormatException e){
                System.out.println("Wrong number format. Re-enter it please: ");
            } catch (NegativeFieldException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private static StandardOfLiving askStandardOfLiving(Scanner InputScanner) {
        while (true){
            try {
                String type = InputScanner.nextLine().toUpperCase();
                StandardOfLiving standard;
                switch (type){
                    case "HIGH":
                        standard = StandardOfLiving.HIGH;
                        break;
                    case "LOW":
                        standard = StandardOfLiving.LOW;
                        break;
                    case "NIGHTMARE":
                        standard = StandardOfLiving.NIGHTMARE;
                        break;
                    default:
                        throw new EmptyFieldException("No such standard of living. " +
                                "Re-enter it correctly (HIGH, LOW, NIGHTMARE): ");
                }
                return standard;
            } catch(NullPointerException e){
                System.out.println("Null standard of living found.");
            } catch (EmptyFieldException e){
                System.out.println("Wrong format of standard of living.");
            }
        }
    }

    private static int askInt(Scanner InputScanner) {
        while(true) {
            try {
                int num = Integer.parseInt(InputScanner.nextLine());
                if (num > 0){
                    return num;
                } else {
                    throw new NegativeFieldException("Wrong number format (it can't be less than 0). Re-enter it please: ");
                }

            } catch (NumberFormatException e){
                System.out.println("Wrong number format. Re-enter it please: ");
            } catch (NegativeFieldException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private static long askLong(Scanner InputScanner) {
        while(true) {
            try {
                long num = Long.parseLong(InputScanner.nextLine());
                if (num > 0){
                    return num;
                } else {
                    throw new NegativeFieldException("Wrong number format (it can't be less than 0). Re-enter it please: ");
                }

            } catch (NumberFormatException e){
                System.out.println("Wrong number format. Re-enter it please: ");
            } catch (NegativeFieldException e){
                System.out.println(e.getMessage());
            }
        }
    }
    public static String askGovernorName(Scanner InputScanner){
        while(true) {
            var name = InputScanner.nextLine();
            if (!name.isBlank()){
                return name.trim();
            }
            else if(name.isBlank()) {
                return "null";
            }
        }
    }

    public static CityModel createElement(User user){
        Scanner InputScanner = PromptScan.getUserScanner();

        System.out.println("Enter name: ");
        String name = askString(InputScanner);

        System.out.println("Enter x coordinate: ");
        float x = askX(InputScanner);

        System.out.println("Enter y coordinate: ");
        double y = askY(InputScanner);

        Coordinates coordinates = new Coordinates(x, y);

        System.out.println("Enter area: ");
        int area = askInt(InputScanner);

        System.out.println("Enter population: ");
        int population = askInt(InputScanner);

        System.out.println("Enter meters above the sea level: ");
        Float metersAboveSeaLevel = askFloat(InputScanner);

        System.out.println("Enter phone code: ");
        Long telephoneCode = askLong(InputScanner);

        System.out.println("Enter agglomeration: ");
        long agglomeration = askLong(InputScanner);

        System.out.print("""
                Enter ONE of available standards of living:
                HIGH
                LOW
                NIGHTMARE
                """);
        StandardOfLiving standard = askStandardOfLiving(InputScanner);


        System.out.println("Enter the governor's name (if you want it to be null -- enter \"null\" in any case: ");
        Human governor = new Human(askGovernorName(InputScanner));
        if (governor.getName().equalsIgnoreCase("null")){
            governor = null;
        }

        return new CityModel(name, coordinates, area, population, metersAboveSeaLevel, telephoneCode, agglomeration, standard, governor, user);
    }
}