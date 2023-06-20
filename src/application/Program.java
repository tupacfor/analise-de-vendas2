package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.next();
		System.out.println();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Sale> list = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}

			Set<String> listSellers = list.stream().map(x -> x.getSeller()).collect(Collectors.toSet());
			
			Map<String, Double> sellersSells = new HashMap<>();
			
			for (String s : listSellers) {
				Double sum =  list.stream().filter(x -> x.getSeller().equals(s)).map(x -> x.getTotal()).reduce(0.0, (x, y) -> x + y);
				sellersSells.put(s, sum);
			}
			
			System.out.println("Total de vendas por vendedor: ");
			sellersSells.forEach((x, y) -> System.out.println(x + " - R$ " + String.format("%.2f", y)));
			

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		sc.close();

	}

}
