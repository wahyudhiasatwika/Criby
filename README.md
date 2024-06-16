
## Endpoints

### 1. Predict

#### Description

This endpoint is used to classify the uploaded audio file.

#### Method

- **GET**: Returns an empty response.
- **POST**: Accepts an audio file and returns the classification result.

#### Request

**POST** `/predict`

**Body**:
- The request should contain an audio file.
- Key name: `file`
- Supported audio file extensions: Any

**Example**:

```bash
curl -X POST https://model.criby.app/predict \
  -H "Content-Type: multipart/form-data" \
  -F "file=@/path/to/your/audiofile.ext"
