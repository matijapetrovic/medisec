export interface ServiceLog {
  id: Number,
  sourceIp: String,
  destIp: String,
  path: String,
  protocol: String,
  status: Number, 
  packetSize: Number,
  type: String
}