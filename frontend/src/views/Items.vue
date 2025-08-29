<script setup>
import { ref, onMounted } from 'vue'
import httpClient from '@/http-common'
import { getUserId } from '@/services/authService'

const items = ref([])
const loading = ref(true)
const error = ref('')

// modal state
const showModal = ref(false)
const selectedItemId = ref(null)
const estimatedMinutes = ref(60)
const modalError = ref('')
const submitting = ref(false)

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

function abrirModal(itemId) {
    selectedItemId.value = itemId
    estimatedMinutes.value = 60
    modalError.value = ''
    showModal.value = true
}

function closeModal() {
    showModal.value = false
    selectedItemId.value = null
    estimatedMinutes.value = 60
    modalError.value = ''
}

async function confirmarPrestamo() {
    modalError.value = ''
    const minutes = Number(estimatedMinutes.value)

    if (!selectedItemId.value) {
        modalError.value = 'No se encontró el ítem seleccionado.'
        return
    }
    if (!Number.isInteger(minutes) || minutes <= 0) {
        modalError.value = 'Debes ingresar un número entero mayor que 0.'
        return
    }

    const userId = getUserId()
    if (!userId) {
        modalError.value = 'No se encontró el usuario en sesión.'
        return
    }

    submitting.value = true
    httpClient.post('/api/v1/loan', {
        userId,
        itemId: selectedItemId.value,
        estimatedMinutes: minutes
    }).then(() => {
        closeModal();
        alert('Préstamo solicitado correctamente.');
    }).catch((reason) => {
        if (reason.status == 403) {
            router.push("/");
            return;
        }

        modalError.value =
            e?.response?.data?.message ||
            e?.response?.data?.error ||
            'No se pudo solicitar el préstamo.';
    }).finally(() => {
        submitting.value = false
    });
}
</script>

<template>
    <div class="bg-gray-100">
        <section class="catalog">
            <header class="catalog__header flex items-center justify-between">
                <h1>Catálogo</h1>
            </header>

            <div v-if="loading" class="catalog__state">Cargando ítems…</div>
            <div v-else-if="error" class="catalog__state catalog__state--error">{{ error }}</div>

            <!-- Grid de 3 columnas -->
            <div v-else class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
                <div v-for="it in items" :key="it.id"
                    class="catalog__item bg-white border border-surface-200 rounded p-4">
                    <div class="row"><span class="label">Nombre:</span><span class="value">{{ it.name }}</span></div>
                    <div class="row"><span class="label">Descripción:</span><span class="value">{{ it.description
                            }}</span></div>
                    <div class="row"><span class="label">Tipo:</span><span class="value">{{ it.type ?? '—' }}</span>
                    </div>
                    <div class="row"><span class="label">Stock:</span><span class="value">{{ it.stock }}</span></div>
                    <div class="row">
                        <span class="label">Disponible:</span>
                        <span class="value" :class="it.available ? 'ok' : 'no'">{{ it.available ? 'Sí' : 'No' }}</span>
                    </div>

                    <div class="mt-3">
                        <button class="bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700 disabled:opacity-60"
                            :disabled="!it.available" @click="abrirModal(it.id)">
                            Solicitar préstamo
                        </button>
                    </div>
                </div>
            </div>

            <div v-if="!loading && !error && items.length === 0" class="catalog__state">
                No hay ítems para mostrar.
            </div>
        </section>

        <!-- Modal Solicitud -->
        <div v-if="showModal" class="fixed inset-0 z-50 flex items-center justify-center">
            <!-- Backdrop -->
            <div class="absolute inset-0 bg-black/40" @click="closeModal"></div>

            <!-- Dialog -->
            <div class="relative bg-white w-full max-w-md mx-4 rounded-xl shadow-xl p-6">
                <h2 class="text-xl font-semibold mb-1">Solicitar préstamo</h2>
                <p class="text-sm text-gray-600 mb-4">
                    Indica el tiempo estimado de uso (en minutos).
                </p>

                <form @submit.prevent="confirmarPrestamo" class="space-y-4">
                    <div>
                        <label for="estimated" class="block text-gray-800 mb-2">Minutos estimados</label>
                        <input id="estimated" v-model.number="estimatedMinutes" type="number" min="1" step="1"
                            class="w-full px-3 py-2 border-2 rounded focus:border-blue-500 focus:outline-none"
                            placeholder="Ej: 60" required />
                    </div>

                    <div v-if="modalError" class="bg-red-50 text-red-700 px-3 py-2 rounded text-sm">
                        {{ modalError }}
                    </div>

                    <div class="flex items-center justify-end gap-3">
                        <button type="button" class="px-4 py-2 rounded border" @click="closeModal"
                            :disabled="submitting">
                            Cancelar
                        </button>
                        <button type="submit"
                            class="px-4 py-2 rounded bg-blue-600 text-white hover:bg-blue-700 disabled:opacity-60"
                            :disabled="submitting">
                            {{ submitting ? 'Enviando…' : 'Confirmar' }}
                        </button>
                    </div>
                </form>
            </div>
        </div>
        <!-- /Modal -->
    </div>
</template>

<style scoped>
.catalog {
    max-width: 1200px;
    margin: 0 auto;
    padding: 1rem;
}

.catalog__header {
    margin-bottom: 1rem;
}

.catalog__state {
    padding: .75rem 1rem;
    background: #f6f6f6;
    border-radius: .5rem;
}

.catalog__state--error {
    background: #ffe8e8;
}

.catalog__item {
    border: 1px solid #ececec;
    border-radius: .75rem;
    padding: .75rem 1rem;
}

.row {
    display: grid;
    grid-template-columns: 100px 1fr;
    gap: .5rem;
    margin: .25rem 0;
}

.label {
    font-weight: 600;
    color: #666;
}

.value.ok {
    color: #087f23;
    font-weight: 600;
}

.value.no {
    color: #b00020;
    font-weight: 600;
}
</style>