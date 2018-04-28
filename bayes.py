from numpy import *
def loadDataSet():
    postingList=[
           ['喜欢','猫咪','非常','漂亮'] ,
           ['丫的','混蛋','真是','傻逼'],
           ['漂亮','裙子','价格','挺贵'],
           ['下午','碰到','混蛋','火大'],
           ['漂亮','衣服','国贸','真好'],
           ['一起','收拾','哪个','混蛋']
    ]
    classVec = [0,1,0,1,0,1]
    return postingList,classVec

def createVocabList(dataSet):
    vocabSet = set([])
    for document in dataSet:
        vocabSet = vocabSet | set(document)
    return list(vocabSet)
def setOfWords2Vec(vocabList,inputSet):
    returnVec = [0] * len(vocabList)
    for word in inputSet:
         if word in vocabList:
             returnVec[vocabList.index(word)] = 1
         else:
             print("the word: %s is not in my Vocabulary!" %word)
    return returnVec
def trainNO0(trainMatrix,trainCategory):
    numTrainDocs = len(trainMatirx)
    numWords = len(trainMatrix[0])
    pAbusive = sum(trainCategory)/float(numTrainDocs)
    p0Num = zeros(numWords); p1Num = zeros(numWords)
    poDenom = 0.0; p1Denom = 0.0
    for i in range(numTrainDocs):
        if trainCategory[i] == 1:
            p1Num += trainMatrix[i]
            p1Denom += sum(trainMatrix[i])
        else:
            p0Num += trainMatrix[i]
            p0Denom += sum(trainMatrix[i])
        p1Vect = p1Num/p1Denom
        p0Vect = p0Num/p0Denom
        return p0Vect,p1Vect,pAbusive



listOPosts,listClasses = loadDataSet()
myVocabList = createVocabList(listOPosts)
print(myVocabList)
print( setOfWords2Vec(myVocabList,listOPosts[0]) )
