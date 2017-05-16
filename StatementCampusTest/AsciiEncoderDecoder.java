
/**
* Sentence contains only characters from a-zA-Z and space.
* Each character's ascii value is used and appended in a string and then the string's reverse is taken to encode the sentence.
* To decode, you have follow the same process to get the sentence. 
* Enchancement : Should be done using Regex for the match and if not then calculate the next character.
**/
public class AsciiEncoderDecoder {
	public static void main(String... args) {
		AsciiEncoderDecoder cipher = new AsciiEncoderDecoder();
		String sentence = "Have a nice day";
		String encodedSentence = cipher.encode(sentence);
		String decodedSentence = cipher.decode(encodedSentence);
		if(sentence.equals(decodedSentence)) {
			System.out.println("Decoding successful");
		} else {
			System.out.printf("Encoding decoding issue. %s %s %s", sentence, encodedSentence, decodedSentence);
		}
	}
	
	public String encode(String sentence) {
		char[] chars = sentence.toCharArray();
		StringBuffer encoded = new StringBuffer();
		for(int i = 0; i < chars.length; i++) {
			encoded.append((int) chars[i]);
		}
		return encoded.reverse().toString();
	}
	
	public String decode(String string) {
		char[] chars = string.toCharArray();
		StringBuffer decoded = new StringBuffer();
		for (int i = chars.length - 1; i >=0; i--) {
			boolean flag = true;
			int character = Integer.parseInt(chars[i] + "");
			while(flag) {
				// Check for space
				if (character == 32) {
					flag = false;
				} else if (character >= 65 && character <= 90) {
					flag = false;
				} else if (character >= 97 && character <= 122) {
					flag = false;
				} else {
					i--;
					character = Integer.parseInt(character + "" + chars[i]);
				}
			}			
			decoded.append((char) character);
		}
		return decoded.toString();
	}
}
