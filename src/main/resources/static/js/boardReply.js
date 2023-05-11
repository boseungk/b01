async function get1(bno) {

    const result = await axios.get(`/boardReplies/list/${bno}`)

    console.log(result)

    return result;
}


// async function getList({bno, page, size, goLast}){
//
//     const result = await axios.get(`/boardReplies/list/${bno}`, {params: {page, size}})
//
//     return result.data
// }


async function getList({bno, page, size, goLast}){

    const result = await axios.get(`/boardReplies/list/${bno}`, {params: {page, size}})

    if(goLast){
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))

        return getList({bno:bno, page:lastPage, size:size})

    }

    return result.data
}


async function addReply(boardReplyObj) {
    const response = await axios.post(`/boardReplies/`,boardReplyObj)
    return response.data
}

async function getReply(rno) {
    const response = await axios.get(`/boardReplies/${rno}`)
    return response.data
}

async function modifyReply(boardReplyObj) {

    const response = await axios.put(`/boardReplies/${boardReplyObj.rno}`, boardReplyObj)
    return response.data
}

async function removeReply(rno) {
    const response = await axios.delete(`/boardReplies/${rno}`)
    return response.data
}
