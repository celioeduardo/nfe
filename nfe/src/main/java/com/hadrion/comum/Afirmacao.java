package com.hadrion.comum;

public class Afirmacao {

    protected Afirmacao() {
        super();
    }

    protected void assertArgumentoEquals(Object objeto1, Object objeto2, String mensagem) {
        if (!objeto1.equals(objeto2)) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    protected void assertArgumentoFalso(boolean boleano, String mensagem) {
        if (boleano) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    protected void assertTamanhoArgumento(String string, int maximo, String mensagem) {
        int length = string.trim().length();
        if (length > maximo) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    protected void assertTamanhoArgumento(String string, int minimo, int maximo, String mensagem) {
        int length = string.trim().length();
        if (length < minimo || length > maximo) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    protected void assertArgumentoNaoVazio(String string, String mensagem) {
        if (string == null || string.trim().isEmpty()) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    protected void assertArgumentoNotEquals(Object objeto1, Object objeto2, String mensagem) {
        if (objeto1.equals(objeto2)) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    public static void assertArgumentoNaoNulo(Object anObject, String mensagem) {
        if (anObject == null) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    protected void assertFaixaArgumento(double valor, double minimo, double maximo, String mensagem) {
        if (valor < minimo || valor > maximo) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    protected void assertFaixaArgumento(float valor, float minimo, float maximo, String mensagem) {
        if (valor < minimo || valor > maximo) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    protected void assertFaixaArgumento(int valor, int minimo, int maximo, String mensagem) {
        if (valor < minimo || valor > maximo) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    protected void assertFaixaArgumento(long valor, long minimo, long maximo, String mensagem) {
        if (valor < minimo || valor > maximo) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    protected void assertArgumentoVerdadeiro(boolean boleano, String mensagem) {
        if (!boleano) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    protected void assertEstadoFalso(boolean boleano, String mensagem) {
        if (boleano) {
            throw new IllegalStateException(mensagem);
        }
    }

    protected void assertEstadoVerdadeiro(boolean boleano, String mensagem) {
        if (!boleano) {
            throw new IllegalStateException(mensagem);
        }
    }
}
