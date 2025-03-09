package application;

import domain.entities.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Locale.setDefault(Locale.US);
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter full file path: ");
    String path = sc.nextLine();

    try (BufferedReader br = new BufferedReader(new FileReader(path))) {

      List<Employee> list = new ArrayList<>();
      String line = br.readLine();

      while (line != null) {
        String[] fields = line.split(",");
        list.add(new Employee(fields[0], fields[1], Double.parseDouble(fields[2])));
        line = br.readLine();
      }

      System.out.print("Enter base salary: ");
      double baseSalary = sc.nextDouble();

      List<String> emails =
          list.stream()
              .filter(employee -> employee.getSalary() > baseSalary)
              .map(Employee::getEmail)
              .sorted()
              .collect(Collectors.toList());

      System.out.println("Email of people whose salary is more than " + String.format("%.2f", baseSalary) + ": ");
      emails.forEach(System.out::println);

      double totalSalary =
          list.stream()
              .filter(employee -> employee.getName().charAt(0) == 'M')
              .map(Employee::getSalary)
              .reduce(0.0, Double::sum);

      System.out.print(
          "Sum of salary of people whose name starts with 'M': "
              + String.format("%.2f", totalSalary));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    sc.close();
    }
}