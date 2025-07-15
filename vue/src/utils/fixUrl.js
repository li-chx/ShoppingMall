import request from "@/utils/request";

export async function fixUrl(url) {
    if(!url || typeof url !== 'string' || url.length === 0) {
        return '';
    }
    if(url.startsWith('http'))
        return url;
    try {
        let result = (await request.get('/files/getFileLink/' + url)).data;
        if (!result || typeof result !== 'string') {
            console.error('Invalid URL returned:', result);
            return '';
        }
        return result;
    } catch (error) {
        console.error('Error fetching file link:', error);
        return '';
    }
}

export async function fixUrlList(structuredUrls, getUrl, setUrl) {
    return await Promise.all(structuredUrls.map(async x => setUrl(x, await fixUrl(getUrl(x)))));
}
