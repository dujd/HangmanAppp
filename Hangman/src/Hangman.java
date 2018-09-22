import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hangman {

	private static BufferedReader bufferedFileReader;
	private static FileReader fileReader;
	static int cat;
	static ArrayList<String> wordList = new ArrayList<>();
	static ArrayList<String> category = new ArrayList<>();
	static int currentTry;
	static int maxTries = 10;
	static boolean playGame = true;
	static int wins = 0;
	static int losses = 0;
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
	
		char replay;
	
		
		while(playGame){
			playHangMan(args , input);
			
			System.out.println("Y|N Would you like to play?" );
			replay = input.next().charAt(0);
			if (Character.toUpperCase(replay) != 'Y'){
				playGame = false;
			}
		}
		input.close();
	}
	public static void playHangMan(String[] args, Scanner input) throws IOException{
		
		wordList = getList(args);
		chooseCategory(cat);
		while (true){
		System.out.println("Please choose category:");
		System.out.println("Football teams\nBooks\nProgramming principles\n");
		String category = (input.nextLine().toLowerCase());
		if (category.equals("football teams"))
		{
			chooseCategory(1);
			break;
		}else if (category.equals("books")){
			chooseCategory(2);
			break;
		}else if (category.equals("programming principles")){
			chooseCategory(3);
			break;
		}else {
			
			System.out.println(category);
			continue;
		} 
		} 
		ArrayList<Character> guessList = new ArrayList<>();
		Random randNum = new Random();
		int wordIndex = Math.abs(randNum.nextInt() % wordList.size());
		String orgWord = wordList.get(wordIndex);
		String word = wordList.get(wordIndex).toLowerCase().replaceAll("\\s|\\-", "");
		/*for (int i = 0; i < wordList.size() ; i++){
			System.out.println(wordList.get(i));
		}*/
		char[] asteriskWord = hideWord(word);
		boolean guessedWord = false;
		
		
		while (!guessedWord)
		{	
			
			displayHiddenWord(asteriskWord);
			if(!checkCharacter(word, asteriskWord, guessList, input)) currentTry++;
			int attemps = maxTries - currentTry;
			System.out.print("Attemps left:"+ attemps + "\n");
			if(didWeLose()){
				System.out.println("Sorry, you lost! \n" + "The word was : " + orgWord.replaceAll("\\B|\\b"," ") + ".");
				lose();
				System.out.println("Wins: "+ wins + "  Losses :"+ losses);
				guessedWord = true;
				
			}
			
			if(checkGameStatus(asteriskWord)){
				System.out.println("Congratulations!You've guessed "+ orgWord.replaceAll("\\B|\\b"," "));
				win();
				System.out.println("Wins: "+ wins + "  Losses :"+ losses);
				guessedWord = true;
				
			}
			//currentTry++;
			
			
		}
	}
	
	public static boolean checkGameStatus(char[] asteriskWord)
	{
		for (int i = 0; i < asteriskWord.length; i++){
			if (asteriskWord[i] == '*')
				return false;
			
		}
		/*if (didWeLose()){
			System.out.print("You have lost!");
			playGame = false;
			return false;
			
		}*/
		return true;
	}
	
	public static int chooseCategory(int cat){
		category.clear();
		switch (cat) {
		case 1:
			for(int i=1;i < 11 ;i++){
				category.add(wordList.get(i));
			
			}
			wordList.clear();
			for(int i = 0; i < category.size(); i++){
				wordList.add(category.get(i));
			}
			break;
		case 2:
			for(int i=12;i < 20 ;i++){
				category.add(wordList.get(i));
			
			}
			wordList.clear();
			for(int i = 0; i < category.size(); i++){
				wordList.add(category.get(i));
			}
			break;
		case 3:
			for(int i=21;i < 30 ;i++){
				category.add(wordList.get(i));
			
			}
			wordList.clear();
			for(int i = 0; i < category.size(); i++){
				wordList.add(category.get(i));
			}
			break;
		default:
			break;
		}
		return cat;
	}
	
	public static boolean checkCharacter(String word, char[] asteriskWord, ArrayList<Character> guessList,Scanner input ){
		char guess = input.next().charAt(0);
		boolean repeatGuess = true;
		boolean foundChar = false;
		
		while (repeatGuess)
		{
			if (!guessList.contains(guess)){
				guessList.add(guess);
				repeatGuess = false;
			}
			else {
				System.out.print("You've already made that guess:" + guessList.toString() + 
						"\nTry another >");
				guess = input.next().charAt(0);
			}
			
			for (int i=0; i <word.length();i++){
				if (word.charAt(i) == guess){
					asteriskWord[i] = guess;
					foundChar = true;
					}
			}
		}
		return foundChar;
	}
	
	public static void displayHiddenWord(char[] asteriskWord){
		System.out.print("Guess a character: ");
		
		for (int i=0;i<asteriskWord.length; i++){
			System.out.print(asteriskWord[i]);
			
		}
		System.out.print(">");
	}
	
	
	/*public static char[] hideWord(String word){
		char[] tempAsteriskWord = new char[word.length()];
		
		for (int i=0;i < tempAsteriskWord.length * 2; i++){
			tempAsteriskWord[i] = '_';
		}
		return tempAsteriskWord;
	}*/
	
	
	public static char[] hideWord(String word){
		StringBuilder current = new StringBuilder();
		for(int i=0;i<word.length();i++){
			current.append("*");
		}
		String wordSt = current.toString();
		char[] wordC = wordSt.toCharArray();
		return wordC;
	}
	
	public static ArrayList<String> getList(String[] args) throws IOException
	{
		File sourceFile = new File("dictionary.txt");
		fileReader = new FileReader(sourceFile);
		bufferedFileReader = new BufferedReader(fileReader);
		String currentLine = bufferedFileReader.readLine();
		
		if (!sourceFile.exists()){
			System.out.print("Missing dictionary.");
			System.exit(1);
		}
		ArrayList<String> tempWordList = new ArrayList<>();
		
			while (currentLine != null){
				tempWordList.add(currentLine);
				currentLine = bufferedFileReader.readLine();
			}
		
		return tempWordList;
	}
	
	public static boolean didWeLose(){
		return currentTry >= maxTries;
	}
	
	public static void win(){
		wins++;
	}
	
	public static void lose(){
		losses++;
	}
	/*
	public static boolean didWeWin(){
		return
	}
	
	
	public boolean playGame(){
		if(didWeWin()){
			System.out.print("Congrats!");
			win();
			return true;
		}else if (didWeLose()){
			System.out.print("Sorry , you lose.");
			lose();
			return true;
		}
		return false;
	}*/
}






