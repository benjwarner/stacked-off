package org.tools4j.stacked.index
class SiteIndexUtils(val classpathToXmlFiles: String, val siteId: String) {

    fun createAndLoadPostIndex(): StagingPostIndex {
        val postIndex = createPostIndex()
        return loadPostIndex(postIndex)
    }

    fun loadPostIndex(stagingPostIndex: StagingPostIndex): StagingPostIndex {
        val xmlFileParser = XmlFileParser(
            Dummy().javaClass.getResourceAsStream("$classpathToXmlFiles/Posts.xml"),
            PostXmlRowHandler(stagingPostIndex.getItemHandler())
        )
        xmlFileParser.parse()
        return stagingPostIndex
    }

    fun createAndLoadCommentIndex(): StagingCommentIndex {
        val commentIndex = createCommentIndex()
        return loadCommentIndex(commentIndex)
    }

    fun loadCommentIndex(stagingCommentIndex: StagingCommentIndex): StagingCommentIndex {
        val xmlFileParser = XmlFileParser(
            Dummy().javaClass.getResourceAsStream("$classpathToXmlFiles/Comments.xml"),
            CommentXmlRowHandler(stagingCommentIndex.getItemHandler())
        )
        xmlFileParser.parse()

        return stagingCommentIndex
    }

    fun createAndLoadUserIndex(): StagingUserIndex {
        val userIndex = createUserIndex()
        return loadUserIndex(userIndex)
    }

    fun loadUserIndex(stagingUserIndex: StagingUserIndex): StagingUserIndex {
        val xmlFileParser = XmlFileParser(
            Dummy().javaClass.getResourceAsStream("$classpathToXmlFiles/Users.xml"),
            UserXmlRowHandler(stagingUserIndex.getItemHandler())
        )
        xmlFileParser.parse()

        return stagingUserIndex
    }

    fun createUserIndex(): StagingUserIndex {
        val userIndex = StagingUserIndex(getTestIndexFactory())
        userIndex.init()
        userIndex.getItemHandler().onFinish()
        return userIndex
    }

    //TODO, work how to get resourceAsStream easier
    class Dummy {}

    fun createStagingIndexes(): StagingIndexes {
        return StagingIndexes(
            createPostIndex(),
            createCommentIndex(),
            createUserIndex()
        )
    }

    fun createAndLoadStagingIndexes(): StagingIndexes {
        return StagingIndexes(
            createAndLoadPostIndex(),
            createAndLoadCommentIndex(),
            createAndLoadUserIndex()
        )
    }
}

