<template>
  <div class="p-6 max-w-lg mx-auto">
    <h2 class="text-2xl font-bold mb-4">Crear nuevo Item</h2>

    <form @submit.prevent="handleSubmit" class="space-y-4">
      <!-- Nombre -->
      <div>
        <label class="block mb-1 font-semibold">Nombre</label>
        <InputText v-model="form.name" class="w-full" placeholder="Ej: UNO" required />
      </div>

      <!-- Descripción -->
      <div>
        <label class="block mb-1 font-semibold">Descripción</label>
        <Textarea v-model="form.description" class="w-full" autoResize rows="3" placeholder="Descripción del item" required />
      </div>

      <!-- Tipo -->
      <div>
        <label class="block mb-1 font-semibold">Tipo</label>
        <Dropdown 
          v-model="form.type"
          :options="types"
          optionLabel="label"
          optionValue="value"
          placeholder="Selecciona un tipo"
          class="w-full"
        />
      </div>

      <!-- Stock -->
      <div>
        <label class="block mb-1 font-semibold">Stock</label>
        <InputNumber v-model="form.stock" :min="1" class="w-full" />
      </div>

      <!-- Mensaje de error -->
      <div v-if="error" class="p-2 bg-red-100 text-red-600 rounded">
        {{ error }}
      </div>

      <!-- Botón -->
      <div class="flex justify-end">
        <Button label="Guardar" icon="pi pi-check" type="submit" />
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import InputText from 'primevue/inputtext'
import Textarea from 'primevue/textarea'
import Dropdown from 'primevue/dropdown'
import InputNumber from 'primevue/inputnumber'
import Button from 'primevue/button'
import httpClient from '@/http-common';

const form = ref({
  name: '',
  description: '',
  type: null,
  stock: 1,
})

const types = [
  { label: 'Juego', value: 'GAME' },
  { label: 'Consola', value: 'CONSOLE' },
]

const error = ref('')

const handleSubmit = async () => {
  try {
    error.value = ''
    await httpClient.post('/api/v1/admin/item', form.value)
    // Limpia el formulario
    form.value = { name: '', description: '', type: null, stock: 1 }
    alert('Item creado exitosamente ✅')
  } catch (e) {
    error.value = 'No se pudo crear el item'
    console.error(e)
  }
}
</script>
