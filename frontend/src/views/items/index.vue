<template>
  <section class="catalog">
    <header class="catalog__header">
      <h1>Catálogo</h1>
    </header>

    <div v-if="loading" class="catalog__state">Cargando ítems…</div>
    <div v-else-if="error" class="catalog__state catalog__state--error">{{ error }}</div>

    <ul v-else class="catalog__list">
      <li v-for="it in items" :key="it.id" class="catalog__item">
        <div class="row"><span class="label">Nombre:</span><span class="value">{{ it.name }}</span></div>
        <div class="row"><span class="label">Descripción:</span><span class="value">{{ it.description }}</span></div>
        <div class="row"><span class="label">Tipo:</span><span class="value">{{ it.type ?? '—' }}</span></div>
        <div class="row"><span class="label">Stock:</span><span class="value">{{ it.stock }}</span></div>
        <div class="row">
          <span class="label">Disponible:</span>
          <span class="value" :class="it.available ? 'ok' : 'no'">
            {{ it.available ? 'Sí' : 'No' }}
          </span>
        </div>
      </li>
    </ul>

    <div v-if="!loading && !error && items.length === 0" class="catalog__state">
      No hay ítems para mostrar.
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import httpClient from '@/http-common';

const items = ref([])
const loading = ref(true)
const error = ref('')

onMounted(async () => {
  try {
    const { data } = await httpClient.get('/api/v1/item')
    items.value = Array.isArray(data) ? data : []
  } catch (e) {
    error.value = 'No se pudo cargar el catálogo.'
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.catalog { max-width: 800px; margin: 0 auto; padding: 1rem; }
.catalog__header { margin-bottom: 1rem; }
.catalog__state { padding: .75rem 1rem; background: #f6f6f6; border-radius: .5rem; }
.catalog__state--error { background: #ffe8e8; }
.catalog__list { display: grid; gap: 1rem; }
.catalog__item { border: 1px solid #ececec; border-radius: .75rem; padding: .75rem 1rem; }
.row { display: grid; grid-template-columns: 140px 1fr; gap: .5rem; margin: .25rem 0; }
.label { font-weight: 600; color: #666; }
.value.ok { color: #087f23; font-weight: 600; }
.value.no { color: #b00020; font-weight: 600; }
</style>
