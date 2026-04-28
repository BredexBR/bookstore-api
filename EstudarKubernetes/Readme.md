# ☸️ Kubernetes Study Notes - Spring Boot 2025

Este repositório contém as anotações práticas e comandos do módulo de Kubernetes do curso **"Spring Boot 2025: Do Zero à Amazon AWS e Google Cloud"**.

---

## 📑 Tópicos de Estudo

### 1. 🏗️ Cluster & Monitoramento (Observabilidade)
Comandos para entender o que acontece no cluster e auditar eventos.

* **Listar eventos (Ordenados por tempo):**
    ```bash
    kubectl get events --sort-by=.metadata.creationTimestamp
    ```
* **Informações da versão:** 
    ```bash
    kubectl version
    ```

### 2. 📦 Pods (Ciclo de Vida e Resiliência)
Pods são efêmeros. Se um pod é deletado, o ReplicaSet garante a criação de um novo.

* **Listagem detalhada (IP e Node):**
    ```bash
    kubectl get pods -o wide
    ```
* **Simular falha (Self-healing):**
    Delete um pod e observe o Kubernetes criando outro automaticamente para manter o estado desejado.
    ```bash
    kubectl delete pod [NOME_DO_POD]
    ```
* **Inspeção:** 
    ```bash
    kubectl describe pod [NOME_DO_POD]
    ```
* **Documentação:** 
    ```bash
    kubectl explain pods
    ```

### 3. 🔄 ReplicaSet (Escalabilidade)
Responsável por manter o número exato de réplicas rodando.

* **Listar ReplicaSets:** 
    ```bash
    kubectl get rs
    ```
* **ou:** 
    ```bash
    kubectl get replicasets
    ```
* **Escalamento Horizontal (Scaling):**
    Aumentar ou diminuir o número de instâncias da aplicação.
    ```bash
    kubectl scale deployment hello-kubernetes --replicas=3
    ```

### 4. 🚀 Deployment (Atualização e Versionamento)
O Deployment gerencia os ReplicaSets e permite atualizações de versão sem downtime (Rolling Updates).

* **Atualizar versão da imagem:**
    Altera a imagem do container para uma nova versão (ex: `0.0.2`).
    ```bash
    kubectl set image deployment hello-kubernetes hello-kubernetes=leandrocgsi/hello-kubernetes:erudio-0.0.2
    ```

* **Acompanhar status da atualização (Rollout):**
    Útil para verificar em tempo real se a nova versão foi implantada com sucesso.

    ```Bash
    kubectl rollout status deployment hello-kubernetes
    ```
* **Gerenciar Variáveis de Ambiente:**
    Define ou altera configurações do container sem precisar mudar a imagem Docker.

    ```Bash
    kubectl set env deployment/hello-kubernetes APP_COLOR=blue
    ```

* **Verificar alteração:**
    ```bash
    kubectl get replicaset -o wide
    ```

### 5. 🌐 Services (Rede e Exposição)
O Service provê um ponto de acesso estável (IP/DNS) para os pods, que mudam de IP constantemente.

* **Criar Service (Via Expose):**
    ```bash
    kubectl expose deployment hello-kubernetes --type=LoadBalancer --port=8080
    ```
* **Listar Endereços:**
    ```bash
    kubectl get services
    ```

### 6. 📊 Web UI (Dashboard)
Interface gráfica para gerenciamento do cluster.

* **Instalação:**
    ```bash
    kubectl apply -f [https://raw.githubusercontent.com/kubernetes/dashboard/v2.7.0/aio/deploy/recommended.yaml](https://raw.githubusercontent.com/kubernetes/dashboard/v2.7.0/aio/deploy/recommended.yaml)
    ```
* **Configuração de Acesso (RBAC):**
1. Criar conta de admin: 
    ```bash
    kubectl create serviceaccount admin-user -n kubernetes-dashboard
    ```
2. Criar permissões: 
    ```bash
    kubectl create clusterrolebinding admin-user-binding --clusterrole=cluster-admin --serviceaccount=kubernetes-dashboard:admin-user
    ```
3. Gerar Token: 
    ```bash
    kubectl -n kubernetes-dashboard create token admin-user
    ```
* **Acesso:**
1. Execute o proxy: 
    ```bash
    kubectl proxy
    ```
2. Acesse o [Painel Local](http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/).

---

## 🛠️ Cheat Sheet (Resumo de Comandos)

| Categoria | Comando | Função |
| :--- | :--- | :--- |
| **Escalabilidade** | `kubectl scale --replicas=X` | Altera número de pods ativos |
| **Atualização** | `kubectl set image` | Atualiza a versão da aplicação |
| **Diagnóstico** | `kubectl get events --sort-by=...` | Mostra logs de eventos em ordem cronológica |
| **Rede** | `kubectl get svc` | Lista serviços e IPs de exposição |
| **Segurança** | `kubectl create token` | Gera chave de acesso ao Dashboard |

---

## 🔗 Referências Úteis
* **Imagens do Curso:** [Docker Hub - leandrocgsi](https://hub.docker.com/u/leandrocgsi)
* **Dashboard:** [Official K8s Dashboard Docs](https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/)