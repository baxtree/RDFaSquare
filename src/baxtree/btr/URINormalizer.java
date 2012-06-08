package baxtree.btr;

public class URINormalizer {

	public static String normalize(String uri){
		return uri.replace("&", "&amp;");
	}
	
	public static void main(String[] args){
		System.out.println(URINormalizer.normalize("http://sadjflasjkdf?fds=asdf&fdgs=asdlf"));
	}
}
