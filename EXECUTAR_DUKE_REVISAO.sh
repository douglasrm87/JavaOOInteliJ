#!/bin/bash

# ═════════════════════════════════════════════════════════════════════════════
# GUIA DE EXECUÇÃO - DUKE REVISÃO
# ═════════════════════════════════════════════════════════════════════════════

echo "🚀 DUKE REVISÃO - GUIA DE INICIALIZAÇÃO"
echo "═════════════════════════════════════════════════════════════════════════════"
echo ""

# ─────────────────────────────────────────────────────────────────────────────
# 1. VERIFICAR PRÉ-REQUISITOS
# ─────────────────────────────────────────────────────────────────────────────

echo "📋 1. VERIFICANDO PRÉ-REQUISITOS..."
echo ""

# Verificar Java
echo -n "   Java (v21): "
if command -v java &> /dev/null; then
    java_version=$(java -version 2>&1 | grep "version" | awk '{print $3}' | tr -d '"')
    echo "✓ Instalado ($java_version)"
else
    echo "✗ NÃO ENCONTRADO"
    echo "   ⚠️  Instale Java 21 antes de continuar"
    exit 1
fi

# Verificar Maven
echo -n "   Maven: "
if command -v mvn &> /dev/null; then
    mvn_version=$(mvn -v 2>&1 | head -1)
    echo "✓ Instalado ($mvn_version)"
else
    echo "✗ NÃO ENCONTRADO"
    echo "   ⚠️  Instale Maven antes de continuar"
    exit 1
fi

echo ""

# ─────────────────────────────────────────────────────────────────────────────
# 2. COMPILAR PROJETO
# ─────────────────────────────────────────────────────────────────────────────

echo "🔨 2. COMPILANDO PROJETO..."
echo "   Executando: mvn clean compile"
echo ""

# Ir para diretório do projeto
cd /workspaces/JavaOOInteliJ || exit 1

# Compilar
mvn clean compile

if [ $? -eq 0 ]; then
    echo ""
    echo "   ✅ COMPILAÇÃO SUCESSO!"
else
    echo ""
    echo "   ❌ ERRO NA COMPILAÇÃO"
    exit 1
fi

echo ""

# ─────────────────────────────────────────────────────────────────────────────
# 3. EXECUTAR APLICAÇÃO
# ─────────────────────────────────────────────────────────────────────────────

echo "🚀 3. INICIANDO APLICAÇÃO SPRING BOOT..."
echo ""
echo "   ⏳ Aguarde alguns segundos para a aplicação iniciar..."
echo ""

# Executar Spring Boot
mvn spring-boot:run

# ─────────────────────────────────────────────────────────────────────────────
# 4. ACESSAR NO NAVEGADOR
# ─────────────────────────────────────────────────────────────────────────────

# (Script não pode abrir navegador automaticamente em todos os ambientes)
# Será exibido no console quando o servidor estiver pronto

