package view;
import controller.WorkerController;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import model.WorkerModel;

public class WorkerView {
    public WorkerController controller;
    public Scanner scanner;

    public WorkerView() {
        controller = new WorkerController();
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("Worker Management Program");
        System.out.println("1. Add a Worker");
        System.out.println("2. Increase salary for a Worker");
        System.out.println("3. Decrease salary for a Worker");
        System.out.println("4. Show adjusted salary worker information");
        System.out.println("5. Quit program");
        System.out.print("Enter your choice: ");
    }

    public void run() {
        boolean quit = false;

        while (!quit) {
            displayMenu();

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addWorker();
                    break;
                case 2:
                    adjustSalary(SalaryStatus.INCREASE);
                    break;
                case 3:
                    adjustSalary(SalaryStatus.DECREASE);
                    break;
                case 4:
                    displayAdjustedSalaryWorkers();
                    break;
                case 5:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void addWorker() {
    System.out.println("Enter worker information:");

    System.out.print("ID: ");
    String id = scanner.nextLine();

    if (id.isEmpty() || controller.isDuplicateId(id)) {
        System.out.println("Invalid ID. ID cannot be null or duplicated with existing ID.");
        return;
    }

    System.out.print("Name: ");
    String name = scanner.nextLine();

    System.out.print("Age: ");
    int age = scanner.nextInt();
    scanner.nextLine();

    if (age < 18 || age > 50) {
        System.out.println("Invalid age. Age must be in the range of 18 to 50.");
        return;
    }

    System.out.print("Salary: ");
    double salary;
    while (true) {
        String salaryInput = scanner.nextLine();
        try {
            salary = Double.parseDouble(salaryInput);
            if (salary > 0) {
                break;
            } else {
                System.out.println("Invalid salary. Salary must be greater than 0.");
                System.out.print("Salary: ");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for salary.");
            System.out.print("Salary: ");
        }
    }


    System.out.print("Work Location: ");
    String workLocation = scanner.nextLine();

    WorkerModel worker = new WorkerModel(id, name, age, salary, workLocation);

    boolean success = controller.addWorker(worker);

    if (success) {
        System.out.println("Worker added successfully.");
    } else {
        System.out.println("Failed to add worker. Please check your input and try again.");
    }
}

    private void adjustSalary(SalaryStatus status) {
    System.out.print("Enter worker code: ");
    String code = scanner.nextLine();

    double amount = 0;
    boolean validInput = false;
    while (!validInput) {
        System.out.print("Enter amount of money: ");
        if (scanner.hasNextDouble()) {
            amount = scanner.nextDouble();
            if (amount > 0) {
                validInput = true;
            } else {
                System.out.println("Invalid input. Amount of money must be greater than 0.");
            }
        } else {
            System.out.println("Invalid input. Please enter a valid amount of money.");
            scanner.nextLine();
        }
    }
    scanner.nextLine();

    boolean success = controller.changeSalary(status, code, amount);

    if (success) {
        System.out.println("Salary adjusted successfully.");
    } else {
        System.out.println("Failed to adjust salary. Please check your input and try again.");
    }
}

    private void displayAdjustedSalaryWorkers() {
    System.out.println("--------------------Display Information Salary-----------------------");
    System.out.println("Code  Name         Age  Salary   Status  Date");

    List<WorkerModel> workers = controller.getInformationSalary();

    for (WorkerModel worker : workers) {
        System.out.printf("%-5s %-11s %3d  %8.2f  %-6s  %s%n",
                worker.getId(), worker.getName(), worker.getAge(),
                worker.getSalary(), worker.getStatus(),
                worker.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
}
}
