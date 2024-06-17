
## Endpoints

### 1. Predict

#### Description

This endpoint is used to classify the uploaded audio file.

#### Method

- **GET**: Returns an empty response or maybe redirected to [criby.app](https://criby.app)
- **POST**: Accepts an audio file and returns the classification result.

#### Request

**POST** `/predict`

**Body**:
- The request should contain an audio file.
- Key name: `file`
- Supported audio file extensions: Any

**Return Example**:
```json
{
    "prediction": "Belly Pain",
    "confidence": 100.0
}
```

**Example**:

```bash
curl -X POST https://model.criby.app/predict \
  -H "Content-Type: multipart/form-data" \
  -F "file=@/path/to/your/audiofile.ext"
