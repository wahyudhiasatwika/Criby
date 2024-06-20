/** @type {import('next').NextConfig} */
const nextConfig = {
    output: 'export',
    reactStrictMode: true,
    productionBrowserSourceMaps: true,
    compress: true,
    images: {
        unoptimized: true,
    },
};

module.exports = nextConfig;
