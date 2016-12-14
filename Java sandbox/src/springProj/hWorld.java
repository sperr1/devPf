package springProj;


public class hWorld {

	/*IT'S BEEN AGES SINCE I'VE DONE JAVA*/
	int cap;
	int Cap;
	int[] letters = new int[26];
	int[] Letters = new int[26];
	public hWorld(){
		this.cap = (int)'a';
		this.Cap = (int)'A';
		for(int i = 0; i<26; i++){
			this.letters[i] = this.cap + i;
			this.Letters[i] = this.Cap + i;
		}
	}
	
	public String toString(){
		String s = new String();
		s = s + String.valueOf(Character.toChars(this.Letters[7]));
		s = s + String.valueOf(Character.toChars(this.letters[4]));
		s = s + String.valueOf(Character.toChars(this.letters[11]));
		s = s + String.valueOf(Character.toChars(this.letters[11]));
		s = s + String.valueOf(Character.toChars(this.letters[14]));
		s = s + " ";
		s = s + String.valueOf(Character.toChars(this.Letters[22]));
		s = s + String.valueOf(Character.toChars(this.letters[14]));
		s = s + String.valueOf(Character.toChars(this.letters[17]));
		s = s + String.valueOf(Character.toChars(this.letters[11]));
		s = s + String.valueOf(Character.toChars(this.letters[3]));
		s = s + "!";
		return s;
	}
	public static void main(String[] args) {
		System.out.println("Hello World!");
		hWorld hw = new hWorld();
		System.out.println(hw.toString());

	}

}
