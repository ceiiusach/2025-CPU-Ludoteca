<script setup>
import { ref, defineProps, onMounted } from 'vue';
import MessageSuccess from '@/components/MessageSuccess.vue';
import httpClient from '@/http-common';

const props = defineProps({
    Email: { type: String, required: true },
    Password: { type: String, required: false }
});


const error = ref(false);
const success = ref(false);
const digits = Array.from({ length: 6 }, () => ref(''));
const inputs = ref([]);

const emit = defineEmits(['onCloseValidation']);

const showEmail = ref(props.Email || '');

onMounted(() => {
    if (!showEmail.value) {
        emit('onCloseValidation');
    }
})

// función para moverse automáticamente al siguiente input
const handleInput = (index, event) => {
    let val = event.target.value.toUpperCase().replace(/[^A-Z0-9]/g, '');
    digits[index].value = val;

    if (val && index < inputs.value.length - 1) {
        inputs.value[index + 1].focus();
    }
};

// función para retroceder con backspace
const handleBackspace = (index, event) => {
    if (event.key === 'Backspace' && !digits[index].value && index > 0) {
        inputs.value[index - 1].focus();
    }
};

const sendRecoveryEmail = async () => {
    error.value = false;

    httpClient.post(`/auth/request?email=${encodeURIComponent(showEmail.value)}`)
        .then((response) => {
            if (response.status === 200) {
                alert('Código reenviado. Revisa tu correo.');
            } else {
                alert('Error al reenviar el código. Inténtalo de nuevo.');
            }
        })
        .catch(() => {
            alert('Error al reenviar el código. Inténtalo de nuevo.');
        });

};

const verify = async () => {
    const code = digits.map(d => d.value).join('');
    if (code.length < 6) {
        alert('Por favor, ingresa el código completo.');
        return;
    }

    httpClient.post(`/auth/validate?email=${encodeURIComponent(showEmail.value)}&code=${encodeURIComponent(code)}`)
        .then((response) => {
            if (response.status === 200) {
                error.value = false;
                const Userdata = {
                    role: null,
                    email: showEmail.value,
                    password: props.Password,
                    name: showEmail.value.split('.')[0],
                }
                httpClient.post('/auth/register', Userdata)
                    .then((res) => {
                        if (res.status === 200) {
                            router.push("/items");
                        } else {
                            alert('Error al registrar el usuario');
                            return;
                        }
                    })
                success.value = true;
                setTimeout(() => {
                    success.value = false;
                    emit('onCloseValidation');
                }, 3000);
            } else {
                error.value = true;
            }
        })
        .catch(() => {
            error.value = true;
        });
};
</script>

<template>
    <div class="fixed inset-0 flex items-center justify-center z-[9999] bg-black/30">
        <MessageSuccess v-if="success" message="Código Validado" />
        <div class="w-[70rem] h-[30rem] bg-white rounded-xl shadow-lg p-8 flex flex-col justify-between relative">
            <button
                class="absolute top-2 right-4 text-gray-400 hover:text-gray-500 text-2xl font-bold focus:outline-none"
                @click="emit('onCloseValidation')" aria-label="Cerrar">
                &times;
            </button>
            <h1 class="text-5xl font-bold text-center text-blue-600">Ingresa el código</h1>
            <p class="text-lg text-gray-700 text-center mt-4">
                Se envió un código a {{ showEmail }}
            </p>

            <!-- inputs -->
            <div class="flex justify-center gap-3 mt-6 h-1/2">
                <input v-for="(digit, i) in digits" :key="i" ref="inputs" v-model="digit.value" type="text"
                    maxlength="1"
                    class="w-1/6 h-full bg-white text-center text-black text-5xl font-bold uppercase border-2 border-blue-400 rounded-lg focus:outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-500"
                    @input="handleInput(i, $event)" @keydown="handleBackspace(i, $event)" />
            </div>

            <p class="text-blue-700 text-lg mt-5 font-semibold text-center cursor-pointer hover:underline"
                @click="sendRecoveryEmail">
                Reenviar código
            </p>
            <p v-if="error" class="text-red-500 font-semibold">Codigo invalido, porfavor intentelo nuevamente</p>
            <div class="flex justify-center mt-6">
                <button class="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-6 py-2 rounded-lg transition"
                    @click="verify">
                    Verificar código
                </button>
            </div>
        </div>
    </div>
</template>