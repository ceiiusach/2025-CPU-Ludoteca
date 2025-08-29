import httpClient from '@/http-common'
import { jwtDecode } from 'jwt-decode'

export type LoginRequest = {
  email: string
  password: string
}

export type AuthResponse = {
  token: string
  user_id?: string
  userId?: string
  id?: string
}

const TOKEN_KEY = 'auth_token'
const USER_ID = 'user_id'

function extractUserIdFromToken(token: string): string | null {
  try {
    const payload: any = jwtDecode(token)
    // Ajusta según tus claims reales en el JWT
    return payload?.user_id ?? payload?.userId ?? payload?.sub ?? null
  } catch {
    return null
  }
}

export async function login(payload: LoginRequest) {
  // Asegúrate que esta ruta coincide con tu backend:
  // antes usabas /api/v1/auth/login
  const { data } = await httpClient.post<AuthResponse>('/auth/login', payload)

  const token = data?.token
  if (!token) throw new Error('No se recibió token')

  // Probamos múltiples nombres de campo
  let userId = data?.user_id ?? data?.userId ?? data?.id ?? null
  if (!userId) {
    // Fallback: lo obtenemos del JWT
    userId = extractUserIdFromToken(token)
  }
  if (!userId) {
    // Si aún no lo tenemos, mejor fallar explícitamente
    throw new Error('No se recibió user_id y no se pudo extraer del token')
  }

  localStorage.setItem(TOKEN_KEY, token)
  localStorage.setItem(USER_ID, String(userId))
  return token
}

export function getToken(): string | null {
  return localStorage.getItem(TOKEN_KEY)
}

export function getUserId(): string | null {
  // 1) intenta desde localStorage
  const cached = localStorage.getItem(USER_ID)
  if (cached) return cached
  // 2) fallback: decodifica el token
  const token = getToken()
  if (!token) return null
  const fromToken = extractUserIdFromToken(token)
  if (fromToken) {
    localStorage.setItem(USER_ID, String(fromToken))
  }
  return fromToken
}

export function logout() {
  localStorage.removeItem(TOKEN_KEY)
  localStorage.removeItem(USER_ID)
}
