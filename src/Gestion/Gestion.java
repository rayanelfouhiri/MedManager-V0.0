package Gestion;
import java.util.*;

public class Gestion {
		static final int MAX_PATIENTS = 100;
	    static String[] nomsPatients = new String[MAX_PATIENTS];
	    static String[] prenomsPatients = new String[MAX_PATIENTS];
	    static int[] anneesNaissance = new int[MAX_PATIENTS];
	    static String[] nomsServices = {"Cardiologie", "Urgences", "Pédiatrie"};
	    static int[] capacitesMax = {10, 50, 20};
	    static int[] nbPatientsActuels = {0, 0, 0};
	    static String[] patientServices = new String[MAX_PATIENTS];
	    static int nbPatients = 0;

	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        int choix;

	        do {
	            afficherMenu();
	            choix = lireChoix(scanner);

	            switch (choix) {
	                case 1 -> ajouterPatient(scanner);
	                case 2 -> {trierPatientsParNom();afficherPatients();}
	                case 3 -> rechercherPatient(scanner);
	                case 4 -> afficherStatistique();
	                case 0 -> System.out.println("\n👋 Au revoir !");
	                default -> System.out.println("⚠ Choix invalide.");
	            }
	        } while (choix != 0);

	        scanner.close();
	    }

	    // ── Affichage du menu ──
	    static void afficherMenu() {
	        System.out.println("\n══════ MedManager v0.1 ══════");
	        System.out.println("  1. ➕ Ajouter un patient");
	        System.out.println("  2. 📋 Afficher tous les patients");
	        System.out.println("  3. 🔍 Rechercher un patient");
	        System.out.println("  4. 📊 Statistiques");
	        System.out.println("  0. 🚪 Quitter");
	        System.out.print("Votre choix : ");
	    }

	    // ── Lire un choix entier en toute sécurité ──
	    static int lireChoix(Scanner scanner) {
	        while (!scanner.hasNextInt()) {
	            System.out.print("⚠ Entrez un nombre : ");
	            scanner.next();  // consomme l'entrée invalide
	        }
	        int choix = scanner.nextInt();
	        scanner.nextLine();  // nettoie le buffer
	        return choix;
	    }

	    // ── Ajouter un patient ──
	    static void ajouterPatient(Scanner scanner) {
	        if (nbPatients >= MAX_PATIENTS) {
	            System.out.println("⚠ Capacité maximale atteinte !");
	            return;
	        }

	        System.out.println("\n--- Nouveau Patient ---");

	        System.out.print("Nom : ");
	        nomsPatients[nbPatients] = scanner.nextLine();

	        System.out.print("Prénom : ");
	        prenomsPatients[nbPatients] = scanner.nextLine();
	        
	        int choix = 0;
	        do {
	        	System.out.println("Votre Servie ?");
	        	for(int i = 0;i<nomsServices.length;i++) {
	        	System.out.println(i+ " "+nomsServices[i]);
	        	}
	        	choix = lireChoix(scanner);
	        	
	        	if(choix<0 || choix >= nomsServices.length) {
	        		System.out.println("⚠ Numéro invalide, recommencez.");
	        	}
	        	else if(nbPatientsActuels[choix] >= capacitesMax[choix]) {
	        		System.out.println("⚠ Ce service est complet ! Choisissez-en un autre.");
	                choix = -1;
	        	}
	        }while(choix<0 || choix >= nomsServices.length);
	        patientServices[nbPatients] = nomsServices[choix];
	        nbPatientsActuels[choix]++;
	        
	        int age;
	        do {
	        System.out.print("Année de naissance : ");
	        anneesNaissance[nbPatients] = lireChoix(scanner);
	        age = 2026 - anneesNaissance[nbPatients];
	        if(age <=0 || age >=150) {
	        	System.out.println("INVALIDE !");
	        }
	        }while(age <=0 || age >=150);
	        
	        nbPatients++;
	        
	        

	        System.out.println("✅ Patient enregistré (" + age + " ans)");
	    }

	    // ── Afficher tous les patients ──
	    static void afficherPatients() {
	        if (nbPatients == 0) {
	            System.out.println("\nAucun patient enregistré.");
	            return;
	        }

	        System.out.println("\n--- Liste des Patients ---");
	        System.out.printf("%-4s %-15s %-15s %-10s %-15s%n",
	            "#", "Nom", "Prénom", "Âge" , "Service");
	        System.out.println("─".repeat(45));

	        for (int i = 0; i < nbPatients; i++) {
	            int age = 2026 - anneesNaissance[i];
	            System.out.printf("%-4d %-15s %-15s %-10d %-15s%n",
	                (i + 1), nomsPatients[i], prenomsPatients[i], age, patientServices[i]);
	        }
	        System.out.println("Total : " + nbPatients + " patient(s)");
	    }

	    // ── Rechercher un patient par nom ──
	    static void rechercherPatient(Scanner scanner) {
	        System.out.print("\nRechercher (nom) : ");
	        String recherche = scanner.nextLine().toLowerCase();
	        boolean trouve = false;

	        for (int i = 0; i < nbPatients; i++) {
	            if (nomsPatients[i].toLowerCase().contains(recherche)) {
	                int age = 2026 - anneesNaissance[i];
	                System.out.println("→ " + prenomsPatients[i] + " "
	                    + nomsPatients[i] + " (" + age + " ans)");
	                trouve = true;
	            }
	        }
	        if (!trouve) {
	            System.out.println("Aucun résultat pour \"" + recherche + "\"");
	        }
	    
	}
	    
	 // ── Afficher Statistique ──
	    static void afficherStatistique() {
	    	if(nbPatients == 0) {
	    		System.out.println("Vide !");
	    		return;
	    	}
	    	
	    	int sommeAges = 0;
	    	int ageMin = 2026 - anneesNaissance[0];
	    	int ageMax = ageMin;
	    	
	    	for(int i =0 ; i< nbPatients ; i++) {
	    		int age = 2026 - anneesNaissance[i];
	    		sommeAges += age;
	    		
	    		if(age < ageMin) {
	    			ageMin =age;
	    		}
	    		
	    		if(age > ageMax) {
	    			ageMax = age;
	    		}
	    	}
	    	
	    	double moyenne = (double) sommeAges / nbPatients;
	    	System.out.println("\n--- 📊 STATISTIQUES GLOBALES ---");
	        System.out.println("• Nombre de patients : " + nbPatients);
	        System.out.println("• Âge moyen          : " + String.format("%.1f", moyenne) + " ans");
	        System.out.println("• Âge le plus élevé  : " + ageMax + " ans");
	        System.out.println("• Âge le plus bas    : " + ageMin + " ans");
	        System.out.println("--------------------------------");
	    }
	    
	    
	    // ── Tri Par Nom ──
	    static void trierPatientsParNom() {
	        for(int i = 0 ; i<nbPatients ; i++) {
	        	for(int j = 0 ; j<nbPatients -1 ; j++) {
	        		
	        		if(nomsPatients[j].compareToIgnoreCase(nomsPatients[j+1])>0) {
	        			
	        			String TempNom = nomsPatients[j];
	        			nomsPatients[j] = nomsPatients[j+1];
	        			nomsPatients[j+1] = TempNom;
	        			
	        			String TempPrenom = prenomsPatients[j];
	        			prenomsPatients[j] = prenomsPatients[j+1];
	        			prenomsPatients[j+1] = TempPrenom;
	        			
	        			int Tempanne = anneesNaissance[j];
	        			anneesNaissance[j] = anneesNaissance[j+1];
	        			anneesNaissance[j+1] = Tempanne;
	        			
	        			String Tempservice = patientServices[j];
	        			patientServices[j] = patientServices[j+1];
	        			patientServices[j+1] = Tempservice;
	        			
	        		}
	        	}
	        }
	    }
}