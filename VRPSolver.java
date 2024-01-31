import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

class Load {
    int id;
    double pickupX, pickupY, dropoffX, dropoffY;

    public Load(int id, double pickupX, double pickupY, double dropoffX, double dropoffY) {
        this.id = id;
        this.pickupX = pickupX;
        this.pickupY = pickupY;
        this.dropoffX = dropoffX;
        this.dropoffY = dropoffY;
    }
}

class Driver {
    List<Load> schedule = new ArrayList<>();
    double totalDriveTime;

    public void addLoad(Load load, double driveTime) {
        schedule.add(load);
        totalDriveTime += driveTime;
    }
}

public class VRPSolver {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java VRPSolver <input_file>");
            System.exit(1);
        }

        String inputFile = args[0];
        List<Load> loads = readInput(inputFile);

        List<Driver> solution = solveVRP(loads);

        printSolution(solution);
    }

    private static List<Load> readInput(String inputFile) {
        List<Load> loads = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                int id = Integer.parseInt(parts[0]);
                String[] pickupCoords = parts[1].substring(1, parts[1].length() - 1).split(",");
                String[] dropoffCoords = parts[2].substring(1, parts[2].length() - 1).split(",");
                double pickupX = Double.parseDouble(pickupCoords[0]);
                double pickupY = Double.parseDouble(pickupCoords[1]);
                double dropoffX = Double.parseDouble(dropoffCoords[0]);
                double dropoffY = Double.parseDouble(dropoffCoords[1]);

                loads.add(new Load(id, pickupX, pickupY, dropoffX, dropoffY));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return loads;
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private static List<Driver> solveVRP(List<Load> loads) {
        List<Driver> solution = new ArrayList<>();
        int driverId = 1;
        
        Collections.sort(loads, new Comparator<Load>() {

            public int compare(Load l1, Load l2) {
                double distancel1 = getDistance(l1.pickupX, l1.pickupY, 0, 0);
                double distancel2 = getDistance(l2.pickupX, l2.pickupY, 0, 0);
                return (int)(distancel1 - distancel2);
            }
        });

        while (!loads.isEmpty()) {
            Driver driver = new Driver();
            double remainingDriveTime = 12 * 60;

            for (int i = 0; i < loads.size(); i++) {
                Load load = loads.get(i);
                double pickupTime = getDistance(load.pickupX, load.pickupY, 0, 0);
                double dropoffTime = getDistance(load.dropoffX, load.dropoffY, load.pickupX, load.pickupY);
                double timeToDepot = getDistance(load.dropoffX, load.dropoffY, 0, 0);
                double driveTime = pickupTime + dropoffTime;

                if (remainingDriveTime - driveTime >= timeToDepot) {
                    driver.addLoad(load, driveTime);
                    remainingDriveTime -= driveTime;
                    loads.remove(i);
                    i--;
                }

                Collections.sort(loads, new Comparator<Load>() {

                    public int compare(Load l1, Load l2) {
                        double distancel1 = getDistance(load.dropoffX, load.dropoffY, l1.pickupX, l1.pickupY);
                        double distancel2 = getDistance(load.dropoffX, load.dropoffY, l2.pickupX, l2.pickupY);
                        return (int)(distancel1 - distancel2);
                    }
                });
            }

            solution.add(driver);
            driverId++;
        }

        return solution;
    }

    public static void printSolution(List<Driver> solution) {

        for(Driver driver: solution) {
            for(int i = 0; i <  driver.schedule.size(); i++) {
                if(i == 0) {
                    if(i == driver.schedule.size() - 1) {
                        System.out.print("[" + driver.schedule.get(i).id + "]");
                    } else {
                        System.out.print("[" + driver.schedule.get(i).id + ", ");
                    }
                } else if(i == driver.schedule.size() - 1) {
                    System.out.print(driver.schedule.get(i).id + "]");
                } else {
                    System.out.print(driver.schedule.get(i).id + ", ");
                }
            }
            System.out.println();
        }
    }
}
