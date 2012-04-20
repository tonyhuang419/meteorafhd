package def;

/**
 * http://www.yankay.com/java-fast-byte-comparison/
 */
public class CompateArrays {

	public int compareTo(byte[] buffer1, int offset1, int length1,
			byte[] buffer2, int offset2, int length2) {
		// Short circuit equal case
		if (buffer1 == buffer2 && offset1 == offset2
				&& length1 == length2) {
			return 0;
		}
		// Bring WritableComparator code local
		int end1 = offset1 + length1;
		int end2 = offset2 + length2;
		for (int i = offset1, j = offset2; i < end1 && j < end2; i++, j++) {
			int a = (buffer1[i] & 0xff);
			int b = (buffer2[j] & 0xff);
			if (a != b) {
				return a - b;
			}
		}
		return length1 - length2;
	}

	public static void main(String[] args) {
		byte[] a = {'a','b'};
		byte[] b = {'a','b'};
		for(byte i : a){
			System.out.println(i);
		}
		CompateArrays ca = new CompateArrays();
		System.out.println(ca.compareTo(a, 0, a.length, b, 0, b.length));

	}

}
