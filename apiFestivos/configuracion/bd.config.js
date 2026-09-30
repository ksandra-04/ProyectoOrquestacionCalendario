module.exports = {
    SERVIDOR: process.env.DB_HOST || 'dockerbdfestivos',
    PUERTO: process.env.DB_PORT || '27017',
    BASEDATOS: process.env.DB_NAME || 'festivos',
    USUARIO: process.env.DB_USER || '',
    CLAVE: process.env.DB_PASS || ''
}
