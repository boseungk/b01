async function get1(bno) {

    const result = await axios.get(`/newsReplies/list/${bno}`)

    console.log(result)

    return result;
}


// async function getList({bno, page, size, goLast}){
//
//     const result = await axios.get(`/newsReplies/list/${bno}`, {params: {page, size}})
//
//     return result.data
// }


async function getList({bno, page, size, goLast}){

    const result = await axios.get(`/newsReplies/list/${bno}`, {params: {page, size}})

    if(goLast){
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))

        return getList({bno:bno, page:lastPage, size:size})

    }

    return result.data
}


async function addReply(newsReplyObj) {
    const response = await axios.post(`/newsReplies/`,newsReplyObj)
    return response.data
}

async function getReply(rno) {
    const response = await axios.get(`/newsReplies/${rno}`)
    return response.data
}

async function modifyReply(newsReplyObj) {

    const response = await axios.put(`/newsReplies/${newsReplyObj.rno}`, newsReplyObj)
    return response.data
}

async function removeReply(rno) {
    const response = await axios.delete(`/newsReplies/${rno}`)
    return response.data
}
