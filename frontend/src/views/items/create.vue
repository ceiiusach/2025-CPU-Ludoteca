<template>
  <div class="min-h-screen w-screen flex bg-gray-100">
    <div class="w-full max-w-2xl mx-auto py-10 px-6">
      <h1 class="text-3xl font-bold mb-6 text-center">Crear Item</h1>

      <form @submit.prevent="onSubmit" class="space-y-5 bg-white p-6 rounded-xl shadow">
        <div>
          <label class="block text-gray-800 mb-2" for="name">Nombre</label>
          <input
            v-model.trim="form.name"
            id="name"
            type="text"
            class="w-full px-3 py-2 border-2 rounded focus:border-blue-500 focus:outline-none"
            required
            maxlength="100"
            placeholder="Ej: UNO"
          />
        </div>

        <div>
          <label class="block text-gray-800 mb-2" for="description">Descripción</label>
          <textarea
            v-model.trim="form.description"
            id="description"
            class="w-full px-3 py-2 border-2 rounded focus:border-blue-500 focus:outline-none"
            rows="3"
            required
            placeholder="Breve descripción del item"
          />
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label class="block text-gray-800 mb-2" for="type">Tipo</label>
            <select
              v-model="form.type"
              id="type"
              class="w-full px-3 py-2 border-2 rounded focus:border-blue-500 focus:outline-none"
              required
            >
              <option disabled value="">Selecciona un tipo</option>
              <option value="GAME">GAME</option>
              <option value="CONSOLE">CONSOLE</option>
            </select>
          </div>

          <div>
            <label class="block text-gray-800 mb-2" for="stock">Stock</label>
            <input
              v-model.number="form.stock"
              id="stock"
              type="number"
              min="0"
              step="1"
              class="w-full px-3 py-2 border-2 rounded focus:border-blue-500 focus:outline-none"
              required
              placeholder="Ej: 1"
            />
          </div>
        </div>

        <div v-if="error" class="bg-red-50 text-red-700 px-4 py-3 rounded">
          {{ error }}
        </div>

        <div class="flex items-center gap-3">
          <button
            type="submit"
            :disabled="loading"
            class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 disabled:opacity-60"
          >
            {{ loading ? 'Creando…' : 'Crear item' }}
          </button>

          <router-link
            to="/items"
            class="text-blue-600 hover:text-blue-800 underline"
          >
            Volver al catálogo
          </router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import httpClient from '@/http-common' // ya con interceptor de Authorization

type ItemType = 'GAME' | 'CONSOLE'

const router = useRouter()
const loading = ref(false)
const error = ref('')

const form = reactive({
  name: '',
  description: '',
  type: '' as '' | ItemType,
  stock: 1 as number
})

function validate(): string | null {
  if (!form.name.trim()) return 'El nombre es obligatorio.'
  if (!form.description.trim()) return 'La descripción es obligatoria.'
  if (form.type !== 'GAME' && form.type !== 'CONSOLE') return 'Debes seleccionar un tipo válido.'
  if (!Number.isInteger(form.stock) || form.stock < 0) return 'El stock debe ser un entero mayor o igual a 0.'
  return null
}

async function onSubmit() {
  const v = validate()
  if (v) {
    error.value = v
    return
  }
  loading.value = true
  error.value = ''
  try {
    await httpClient.post('/api/v1/admin/item', {
      name: form.name.trim(),
      description: form.description.trim(),
      type: form.type,
      stock: form.stock
    })
    router.push('/items')
  } catch (e: any) {
    // intenta mostrar mensaje del backend si existe
    error.value =
      e?.response?.data?.message ||
      e?.response?.data?.error ||
      e?.message ||
      'No se pudo crear el item.'
  } finally {
    loading.value = false
  }
}
</script>
