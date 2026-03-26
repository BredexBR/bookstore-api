package br.com.erudio.integrationtests.testConnection;
import org.testcontainers.DockerClientFactory;
import com.github.dockerjava.api.model.Info;

public class DockerCheck {
    public static void main(String[] args) {
        System.out.println("--- Iniciando Diagnóstico de Conexão Docker ---");

        try {
            // 1. Verifica se o Docker está disponível de forma geral
            boolean isDockerAvailable = DockerClientFactory.instance().isDockerAvailable();

            if (isDockerAvailable) {
                System.out.println("✅ Sucesso! O Java encontrou o Docker Desktop.");

                // 2. Tenta buscar informações detalhadas da Engine (SO, Versão, etc)
                Info info = DockerClientFactory.instance().client().infoCmd().exec();
                System.out.println("🐳 Docker OS: " + info.getOperatingSystem());
                System.out.println("🖥️  Arquitetura: " + info.getArchitecture());
                System.out.println("📦 Containers Rodando: " + info.getContainersRunning());
            } else {
                System.out.println("❌ O Docker Desktop não foi encontrado pelo Testcontainers.");
            }
        } catch (Exception e) {
            System.err.println("💥 Falha na comunicação:");
            System.err.println("Mensagem: " + e.getMessage());
            // Se o erro for de 'Permission Denied' ou 'Npipe', o problema é o Contexto do Docker.
        }
    }
}