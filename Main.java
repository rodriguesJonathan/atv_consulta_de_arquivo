import java.util.Scanner;
import java.util.Arrays; 
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.ClassNotFoundException;


class Main {
	public static void main(String[] args) {
		try{
			File file = new File(args[0]);

			//Questão 1
			System.out.println("Quantidade de palavras no texto: "+lenWords(file));
			
			//Questão 2
			System.out.print("Lista das maiores palavras: ");
			String[] arrayBiggestWords = biggestWords(file, 5);
			for(String word : arrayBiggestWords){
				System.out.print(word);
				if(word.equals(arrayBiggestWords[4]))
					System.out.print(".");
				else
					System.out.print("; ");
			}
			System.out.println();

			//Questão 3
			System.out.println("Número da linha que aparece a palavra \"ção\": "+searchLineOfWord("ção", file));

			//Questão 4
			showFamousVowel(file);
			
		}catch(FileNotFoundException e){
			System.out.println("O arquivo \""+args[0]+"\" (sem aspas) não foi encontrado, verifique o nome e o caminho(path) "
							   + "e tente novamente");
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Nenhum argunmento foi digitado. Coloque o argumento e tente novamente"
							  + "\nEx.:\njava ArquivoJava {nome do arquivoTxt}");
		}
	}

	public static int lenWords(File f) throws FileNotFoundException{
		Scanner sc = new Scanner(f);
		int len;
		for (len = 0; sc.hasNext(); len++)
			sc.next();	
		sc.close();
		return len;
	}

	public static String[] biggestWords(File f, int quantity) throws FileNotFoundException{
		Scanner sc = new Scanner(f);
		HashMap<String, Integer> dictionary = new HashMap<>();
		int sizeDictionary = 0;
		char letter;
		String word, wordPure, minorString;
		int sizeWordPure, minorLength;
		//Analise de cada palavra para capturar as {quantity} maiores
		while(sc.hasNext()){
			word = sc.next();
			wordPure = "";

			
			//criar uma cópia da palavra com algumas remoções
			//remover caracteres que não são letras, como números e pontos
			for(int pos = 0; pos < word.length(); pos++){
				letter = word.charAt(pos);
				if(Character.isLetter(letter))
					wordPure += Character.toLowerCase(letter);
			}

			// Adicionar ao HashMap apenas a quantidade desejada pelo usuário
			if(sizeDictionary < quantity){
				dictionary.put(wordPure, wordPure.length());
				sizeDictionary ++;
				continue;//Voltar para o inicio do for
			}
			//Caso a quantidade desejava seja alcançada,
			//Substituir o 
			sizeWordPure = wordPure.length();
			minorLength = 0;
			minorString = "";
			int length; String string;
			//procurar a palavra do dicionario com o menor tamanho
			for (HashMap.Entry<String, Integer> entry : dictionary.entrySet()){
				string = entry.getKey();
				length = entry.getValue();
				if (minorLength == 0){
					minorLength = length;
					minorString = string;
					continue;					
				}
				
				if (minorLength > length){
					minorLength = length;
					minorString = string;
				}
			}

			//Se a palavra verificada for maior que a menor palavra do dicionario...
			//e se a palavra não existir no dicionaro, substitua ela
			if (sizeWordPure > minorLength && !dictionary.containsKey(wordPure)){
				dictionary.remove(minorString);
				dictionary.put(wordPure, sizeWordPure);
				minorString = "";
			}
		}
		sc.close();
		

		return  Arrays.copyOf(dictionary.keySet().toArray(), quantity, String[].class);
	}

	public static void showFamousVowel(File f) throws FileNotFoundException{
		Scanner sc = new Scanner(f);
		int a = 0, e = 0, i = 0, o = 0, u = 0;
		String word;
		while(sc.hasNext()){
			word = sc.next().toLowerCase();
			for(char letter : word.toCharArray()){
				if (letter == 'a' || letter == 'á' || letter == 'à' || letter == 'ã' || letter == 'â')
					a++;
				else if (letter == 'e' || letter == 'é' || letter == 'è' || letter == 'ẽ' || letter == 'ê')
					e++;
				else if (letter == 'i' || letter == 'í' || letter == 'ì' || letter == 'ĩ' || letter == 'î')
					i++;
				else if (letter == 'o' || letter == 'ó' || letter == 'ò' || letter == 'õ' || letter == 'ô')
					o++;
				else if (letter == 'u' || letter == 'ú' || letter == 'ù' || letter == 'ũ' || letter == 'û')
					u++;
			}
		}
		if (a >= e && a >= i && a >= o && a >= u)
			System.out.println("A vogal mais famosa é \"a\"");
		else if (e >= a && e >= i && e >= o && e >= u)
			System.out.println("A vogal mais famosa é \"e\"");
		else if (i >= a && i >= e && i >= o && i >= u)
			System.out.println("A vogal mais famosa é \"i\"");
		else if (o >= a && o >= e && o >= i && o >= u)
			System.out.println("A vogal mais famosa é \"o\"");
		else if (u >= a && u >= e && u >= i && u >= o)
			System.out.println("A vogal mais famosa é \"u\"");
	}

	public static int searchLineOfWord(String word,File f) throws FileNotFoundException{
		Scanner sc = new Scanner(f);
		int numberLine;
		String sLine;
		for (numberLine = 1; sc.hasNextLine(); numberLine++) {
			sLine = sc.nextLine().toLowerCase();
			if(sLine.contains(word))
				return numberLine;
		}
		sc.close();
		return 0;
	}
}