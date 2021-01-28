public class Final {
	public static Etat e_final;
	public static String config_final;

	public static void Definir_final(Etat etat){
		e_final=etat;
		config_final=String.join("", etat.configuration);
	}

	public static boolean Est_Final(Etat etat){
		return String.join("", etat.configuration).equals(config_final);
	}
}
