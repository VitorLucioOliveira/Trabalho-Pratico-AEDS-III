package classes;

class Criptografia {

    private static final String CHAVE = "MEDATOTAL";

    // Função de substituição usando a chave
    private static byte[] substituicao(byte[] dados, boolean cifrar) {
        byte[] chaveBytes = CHAVE.getBytes();
        byte[] resultado = new byte[dados.length];
        for (int i = 0; i < dados.length; i++) {
            resultado[i] = (byte) (dados[i] + (cifrar ? chaveBytes[i % chaveBytes.length] : -chaveBytes[i % chaveBytes.length]));
        }
        return resultado;
    }

    // Função de transposição usando a chave
    private static byte[] transposicao(byte[] dados, boolean cifrar) {
        int[] posicoes = new int[dados.length];
        for (int i = 0; i < dados.length; i++) {
            posicoes[i] = i;
        }

        // Gerar permutação determinística baseada na chave
        for (int i = 0; i < CHAVE.length(); i++) {
            int pos = CHAVE.charAt(i) % dados.length;
            int temp = posicoes[i % dados.length];
            posicoes[i % dados.length] = posicoes[pos];
            posicoes[pos] = temp;
        }

        byte[] resultado = new byte[dados.length];
        if (cifrar) {
            for (int i = 0; i < dados.length; i++) {
                resultado[i] = dados[posicoes[i]];
            }
        } else {
            for (int i = 0; i < dados.length; i++) {
                resultado[posicoes[i]] = dados[i];
            }
        }

        return resultado;
    }

    public static byte[] cifrar(byte[] dados) {
        byte[] substituido = substituicao(dados, true);
        return transposicao(substituido, true);
    }

    public static byte[] decifrar(byte[] dados) {
        byte[] transposto = transposicao(dados, false);
        return substituicao(transposto, false);
    }
}
