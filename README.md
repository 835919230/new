随机图生成:1.简单的有向弱连通图 2.Waxman图
Dijstra算法的三个题目：1.单源单宿最短路问题（给定加权图G，并给定顶点s和d，求从s到d的最小权重路径。）
                        2.最大通过率路径问题
                          给定加权图G，边上权重表示业务流经该边时的通过比例（大于0小于1）。给定源点s，求从s出发到其他顶点的最大通过率路径。
                        3.带宽约束下的最短路问题
                          给定加权图G，每条边上既有权重，也有capacity。给定源点s，以及带宽需求C。求从s出发到其他顶点的带宽大于C的最小权重路径。